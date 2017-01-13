/* 
 * Copyright (c) 2016 RobotsByTheC. All rights reserved.
 *
 * Open Source Software - may be modified and shared by FRC teams. The code must
 * be accompanied by the BSD license file in the root directory of the project.
 */
package org.usfirst.frc.team3926;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.usfirst.frc.team3926.NakedByteArrayOutputStream;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfInt;
import org.opencv.imgcodecs.Imgcodecs;

/**
 * Serves a Motion JPEG image over HTTP. It supports multiple simultaneous
 * clients and can be started and stopped repeatedly.
 * 
 * @author Ben Wolsieffer
 */
public class VideoServer {

    /**
     * Listens for connections on the server port. This thread sends an HTTP
     * header to each client and them adds them to the streaming list.
     */
    private void acceptConnection(AsynchronousSocketChannel clientSocket) {
        HEADER.rewind();
        try {
            clientSocket.setOption(StandardSocketOptions.SO_SNDBUF, 0);
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        clientSocket.write(HEADER, null, new CompletionHandler<Integer, Void>() {

            @Override
            public void completed(Integer result, Void attachment) {
                synchronized (clientSockets) {
                    clientSockets.add(clientSocket);
                    clientSocketFutures.add(null);
                }
            }

            @Override
            public void failed(Throwable e, Void attachment) {
                System.err.println("Unable to communicate with client: " + e);
                try {
                    clientSocket.close();
                } catch (IOException e1) {
                    System.err.println("Unable to close connection: " + e);
                }
            }
        });
    }

    /**
     * The header that is initially sent when a client connects.
     */
    private static final ByteBuffer HEADER = ByteBuffer.wrap(("HTTP/1.0 200 OK\r\n" +
            "Server: VideoStreamer\r\n" +
            "Cache-Control: no-cache\r\n" +
            "Cache-Control: private\r\n" +
            "Content-Type: multipart/x-mixed-replace;boundary=jpgbound\r\n").getBytes());

    /**
     * The content type that is sent to the client before each image.
     */
    private static final byte[] CONTENT_TYPE = "Content-Type: image/jpeg\r\n".getBytes();
    /**
     * The boundary that is sent between each image.
     */
    private static final byte[] BOUNDARY = "\r\n--jpgbound\r\n".getBytes();

    /**
     * TCP port the server is listens on.
     */
    private final int port;
    /**
     * Matrix that hold the JPEG quality parameters.
     */
    private final MatOfInt qualityParams;

    /**
     * Socket that listens for connections.
     */
    private AsynchronousServerSocketChannel serverSocket;

    /**
     * List of streams for currently connected clients.
     */
    private final ArrayList<AsynchronousSocketChannel> clientSockets = new ArrayList<>(2);
    private final ArrayList<Future<Integer>> clientSocketFutures = new ArrayList<>(1);

    /**
     * Buffer that holds the JPEG data.
     */
    private final MatOfByte compressionBuffer = new MatOfByte();

    /**
     * Buffer that holds the same data as {@link #compressionBuffer}, but as a
     * Java data type.
     */
    private byte[] socketBuffer = new byte[0];

    /**
     * Flag indicating that the server is running.
     */
    private boolean running;

    private NakedByteArrayOutputStream responseBufferOutputStream = new NakedByteArrayOutputStream();

    /**
     * Creates a new {@link VideoServer} that listens on the specified port and
     * uses the specified quality level for the video stream.
     * 
     * @param port the port the listen on
     * @param quality the quality of the JPEG stream
     * 
     * @throws IOException if the socket could not be opened
     * 
     */
    public VideoServer(int port, int quality) throws IOException {
        this.port = port;
        responseBufferOutputStream.write(BOUNDARY);
        responseBufferOutputStream.write(CONTENT_TYPE);
        //responseBufferOutputStream.mark();

        qualityParams = new MatOfInt(Imgcodecs.IMWRITE_JPEG_QUALITY, quality);
    }

    /**
     * Creates a new {@link VideoServer} that listens on the specified port and
     * the deafult quality level.
     * 
     * @param port the port the listen on
     * 
     * @throws IOException if the socket could not be opened
     */
    public VideoServer(int port) throws IOException {
        this(port, 95);
    }

    /**
     * Starts listening for connections to the server
     * 
     * @throws IOException if the server socket cannot be allocated
     */
    public void start() throws IOException {
        // Do nothing if already running.
        if (!running) {
            if (serverSocket == null || !serverSocket.isOpen()) {
                serverSocket = AsynchronousServerSocketChannel.open().bind(
                        new InetSocketAddress(port));
                serverSocket.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {

                    @Override
                    public void completed(AsynchronousSocketChannel result, Void attachment) {
                        acceptConnection(result);
                        if (running) {
                            serverSocket.accept(null, this);
                        }
                    }

                    @Override
                    public void failed(Throwable exc, Void attachment) {
                    }
                });
            }

            running = true;
        }
    }

    /**
     * Stops the server.
     * 
     * @throws IOException if an error occurs while closing the socket
     */
    public void stop() throws IOException {
        // Do nothing if the server is not running.
        if (running) {
            running = false;
            serverSocket.close();
        }
    }

    /**
     * Sends an image to all connected clients. This blocks until the image is
     * sent.
     * 
     * @param image the image to send
     * @throws IOException if the image could not be sent
     */
    public void sendImage(Mat image) throws IOException {
        // Only send if server is running.

        if (running) {
            // Only send if at least one client is connected.
            if (!clientSockets.isEmpty()) {
                // Encode the image as a JPEG.
                Imgcodecs.imencode(".jpg", image, compressionBuffer,
                        qualityParams);
                int size = (int) compressionBuffer.total() *
                        compressionBuffer.channels();
                // Resize the Java buffer to fit the data if necessary
                if (size > socketBuffer.length) {
                    socketBuffer = new byte[size];
                }
                // Copy the OpenCV data to a Java byte[].
                compressionBuffer.get(0, 0, socketBuffer);
                responseBufferOutputStream.reset();

                // Reset response buffer to the end of the header
                //responseBufferOutputStream.markReset();
                // Write content length
                responseBufferOutputStream.write(("Content-Length: " + size +
                        "\r\n\r\n").getBytes());
                // Write image to response buffer
                responseBufferOutputStream.write(socketBuffer, 0, size);
                synchronized (clientSockets) {
                    // Send to all clients.
                    for (int i = 0; i < clientSockets.size(); i++) {
                        AsynchronousSocketChannel cs = clientSockets.get(i);
                        Future<Integer> future = clientSocketFutures.get(i);
                        if (future == null || future.isDone()) {
                            try {
                                if (future != null) {
                                    future.get();
                                }
                                clientSocketFutures.set(i, cs.write(
                                        ByteBuffer.wrap(responseBufferOutputStream.toByteArray())));
                            } catch (InterruptedException | ExecutionException ex) {
                                cs.close();
                                clientSocketFutures.remove(i);
                                clientSockets.remove(i);
                            }
                        }
                    }
                }
            }
        } else {
            throw new IllegalStateException("Server must be running to send an image.");
        }
    }

    /**
     * Gets whether the server is running.
     * 
     * @return true if the server is running
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Gets the port number the server is listening on.
     * 
     * @return the port
     */
    public int getPort() {
        return port;
    }
}

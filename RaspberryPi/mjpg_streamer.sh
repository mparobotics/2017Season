#! /bin/bash
# Streams contours to mjpeg stream, to see what robot sees 
export LD_LIBRARY_PATH=/usr/local/lib/
/usr/local/bin/mjpg_streamer -i "input_file.so -f /home/pi/git/2017Season/RaspberryPi/ -n pic.jpg" -o "output_http.so -p 80 -w /usr/local/www"

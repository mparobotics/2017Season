#! /home/pi/.virtualenvs/cv/bin/python2

import cv2
from networktables import NetworkTables
from grip import GripPythonVI  #

# This is just to unconfuse PyCharm  NOTE: Not needed when using remote interpreter
# try:
#     from cv2 import cv2
# except ImportError:
#     pass

# The scale of the image after it has been resized, Default camera resolution is 640x480 so ratio is 0.5
image_scale = 0.5
# The x side of the camera resolution being used for processing
x_resolution = 320
# The y side of the camera resolution being used for processing
y_resolution = 240


def extra_processing(pipeline, frame):
    """
    Performs extra processing on the pipeline's outputs and publishes data to NetworkTables and an MJPG Stream.
    :param pipeline: the pipeline that just processed an image
    :param frame: the current frame being processed
    :return: None
    """
    # The center of the contour on the x axis
    center_x_positions = []
    # The center of the contour on the y axis
    center_y_positions = []
    # The width of the contour
    widths = []
    # The height of the contour
    heights = []
    # The area of the contour, assuming it is rectangular
    areas = []
    # The index of the contour in the list of all contours
    contour_number = 0
    # A for loop to process each contour individually

    red_range = [230, 255.0]
    green_range = [240, 255.0]
    blue_range = [200, 255.0]

    cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
    # frame = (cv2.inRange(frame, (red_range[0], green_range[0], blue_range[0]),
    #                      (red_range[1], green_range[1], blue_range[1])))
    for contour in pipeline.filter_contours_output:
        x, y, w, h = cv2.boundingRect(contour)
        (center_x_positions.append(
            x * image_scale + (w * image_scale / 2)))  # X and Y are coordinates of the top-left corner
        #  of the bounding box
        center_y_positions.append(y * image_scale + (h * image_scale / 2))
        widths.append(w * image_scale)
        heights.append(h * image_scale)
        areas.append((w * image_scale) * (h * image_scale))
        draw_center = x + (w / 2), y + (h / 2)

        (cv2.drawContours(frame, contour, -1, (255, 0, 120), cv2.FILLED))
        cv2.putText(frame, str(contour_number), draw_center, cv2.FONT_HERSHEY_PLAIN, 1, (0, 0, 0))

        contour_number += 1
    # The table from network tables to add data too
    table = NetworkTables.getTable('/vision/high_goal')
    table.putNumberArray('x', center_x_positions)
    table.putNumberArray('y', center_y_positions)
    table.putNumberArray('width', widths)
    table.putNumberArray('height', heights)
    table.putNumberArray('area', areas)
    return frame


def main():  # TODO optimize

    """
    Grabs image from webcam, processes it with opencv to search for contours, publishes these contours to a NetworkTable
    and also to mjpg-Streamer.
    :return: None
    """
    NetworkTables.initialize(server='roboRIO-3926-FRC.local')  # Change the number to your team number if not MPA
    # Grabs the data from camera on port 0
    cap = cv2.VideoCapture(0)
    cap.set(3, x_resolution)  # width
    cap.set(4, y_resolution)  # height
    # pipeline from the grip generated file
    pipeline = GripPythonVI()
    while cap.isOpened():
        have_frame, frame = cap.read()
        if have_frame:
            pipeline.process(frame)
            # extra_processing(pipeline)
            cv2.imwrite('/home/pi/git/2017Season/RasberryPi/pic.jpg', extra_processing(pipeline, frame))

    print('Stopped Capturing')


if __name__ == '__main__':
    main()

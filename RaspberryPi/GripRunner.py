#! /home/pi/.virtualenvs/cv/bin/python2

import cv2
from networktables import NetworkTables
from grip import GripPythonVI  #
import numpy

# This is just to unconfuse PyCharm  NOTE: Not needed when using remote interpreter
# try:
#     from cv2 import cv2
# except ImportError:
#     pass

# The x side of the camera resolution being used for processing
x_resolution = 320
# The y side of the camera resolution being used for processing
y_resolution = 240


def out_of_x_range(x, w, h):
    if ((x < 106) or (x > 212)) and w*h < 100:  #TODO test for real dimensions
        return False
    else:
        return True


def out_of_y_range(y, w, h):
    if ((y < 80) or (y > 160)) and w*h < 100:
        return False
    else:
        return True


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
    # The table from network tables to add data to
    table = NetworkTables.getTable('/vision/high_goal')
    # red_range = [0, 255]
    # green_range = [100, 255.0]
    # blue_range = [0, 255]
    # frame = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
    # frame = (cv2.inRange(frame, (red_range[0], green_range[0], blue_range[0]),
    #                      (red_range[1], green_range[1], blue_range[1])))
    for contour in pipeline.filter_contours_output:

        # The defining features of a contour. 0, 0 is top left corner
        x, y, w, h = cv2.boundingRect(contour)
        if out_of_x_range(x, w, h) and out_of_y_range(y, w, h):
            # The center of the contour as a coordinate pair
            draw_center = x + (w / 2), y + (h / 2)
            # The coordinates of the top left point of the contour as a string
            coordinates = str(x) + "," + str(y)

            center_x_positions.append(x + (w / 2))  # X and Y are coordinates of the top-left corner of the bounding box
            center_y_positions.append(y + (h / 2))
            widths.append(w)
            heights.append(h)
            areas.append(w * h)

            cv2.drawContours(frame, contour, -1, (255, 0, 120), cv2.FILLED)

            cv2.putText(frame, str(contour_number), draw_center, cv2.FONT_HERSHEY_PLAIN, 1, (0, 0, 0))
            # cv2.putText(frame, coordinates, draw_center, cv2.FONT_HERSHEY_PLAIN, 1, (0, 0, 0))

            contour_number += 1

    table.putNumberArray('center_x', center_x_positions)
    table.putNumberArray('center_y', center_y_positions)
    table.putNumberArray('width', widths)
    table.putNumberArray('height', heights)
    table.putNumberArray('area', areas)
    return frame


def main():  # TODO optimize

    """
    Grabs image from webcam, processes it with opencv to search for contours, publishes these contours to a NetworkTable
    and also to mjpg-Streamer for human use.
    :return: None
    """

    NetworkTables.initialize(server='roboRIO-3926-FRC.local')  # Change the number to your team number if not MPA
    # Grabs the data from camera on port 0
    cap = cv2.VideoCapture(0)
    # pipeline from the grip generated file
    pipeline = GripPythonVI()
    gain = 174
    # Setting width
    cap.set(3, x_resolution)
    # Setting height
    cap.set(4, y_resolution)
    
    # cap.set(cv2.CAP_PROP_GAIN, 1)

    while cap.isOpened():
        # have frame is true is camera exists, frame is the data from camera
        have_frame, frame = cap.read()
        if have_frame:
            pipeline.process(frame)
            cv2.imwrite('/home/pi/git/2017Season/RaspberryPi/pic.jpg', extra_processing(pipeline, frame))

    print('Stopped Capturing')


if __name__ == '__main__':
    main()

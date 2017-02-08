#! /home/pi/.virtualenvs/cv/bin/python2

import cv2
from networktables import NetworkTables
from grip import GripPythonVI  #

# This is just to unconfuse PyCharm  NOTE: Not needed when using remote interpreter
# try:
#     from cv2 import cv2
# except ImportError:
#     pass

image_scale = 0.5
x_resolution = 320
y_resolution = 240


def extra_processing(pipeline, frame):
    """
    Performs extra processing on the pipeline's outputs and publishes data to NetworkTables.
    :param pipeline: the pipeline that just processed an image
    :param frame: the current frame being processed
    :return: None
    """
    center_x_positions = []
    center_y_positions = []
    widths = []
    heights = []
    contour_number = 0
    contour_frame = cv2.resize(frame, (0, 0), fx=1, fy=1)
    for contour in pipeline.filter_contours_output:
        x, y, w, h = cv2.boundingRect(contour)
        (center_x_positions.append(
            x * image_scale + (w * image_scale / 2)))  # X and Y are coordinates of the top-left corner
        #  of the bounding box
        center_y_positions.append(y * image_scale + (h * image_scale / 2))
        widths.append(w * image_scale)
        heights.append(h * image_scale)
        draw_center = x + (w / 2), y + (h / 2)
        (cv2.drawContours(contour_frame, contour, -1, (255, 0, 120), cv2.FILLED))
        cv2.putText(contour_frame, str(contour_number), draw_center, cv2.FONT_HERSHEY_PLAIN, 1, (0, 0, 0))
        contour_number += 1

    table = NetworkTables.getTable('/vision/high_goal')
    table.putNumberArray('x', center_x_positions)
    table.putNumberArray('y', center_y_positions)
    table.putNumberArray('width', widths)
    table.putNumberArray('height', heights)
    return contour_frame


def main():
    # TODO optimize
    """
    Grabs image from webcam, processes it with opencv to search for contours, publishes these contours to a NetworkTable
    and also to mjpg-Streamer.
    :return: None
    """
    NetworkTables.setTeam(3926)
    # NetworkTables.initialize(server='roboRIO-3926-FRC.local')      # TODO check if this needs the server or not
    NetworkTables.initialize()
    cap = cv2.VideoCapture(0)
    cap.set(3, x_resolution)  # width
    cap.set(4, y_resolution)  # height
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

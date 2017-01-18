#! /home/pi/.virtualenvs/cv/bin/python2

import cv2
from networktables import NetworkTables
from grip import GripPythonVI  # TODO change the module and class, if needed


def extra_processing(pipeline):
    """
    Performs extra processing on the pipeline's outputs and publishes data to NetworkTables.
    :param pipeline: the pipeline that just processed an image
    :return: None
    """
    center_x_positions = []
    center_y_positions = []
    widths = []
    heights = []

    for contour in pipeline.filter_contours_output:
        x, y, w, h = cv2.boundingRect(contour)
        center_x_positions.append(x + w / 2)  # X and Y are coordinates of the top-left corner of the bounding box
        center_y_positions.append(y + h / w)
        widths.append(w)
        heights.append(y)

    table = NetworkTables.getTable('/vision/high_goal')
    table.putNumberArray('x', center_x_positions)
    table.putNumberArray('y', center_y_positions)
    table.putNumberArray('width', widths)
    table.putNumberArray('height', heights)


def main():
    NetworkTables.setTeam(3926)
    NetworkTables.initialize(server='roborio-3926-frc.local')
    cap = cv2.VideoCapture(0)
    pipeline = GripPythonVI()
    while cap.isOpened():
        have_frame, frame = cap.read()
        if have_frame:
            pipeline.process(frame)
            extra_processing(pipeline)

    print('Stopped Capturing')
if __name__ == '__main__':
    main()

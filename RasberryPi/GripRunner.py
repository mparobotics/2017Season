#! /home/pi/.virtualenvs/cv/bin/python2

import cv2.cv2 as cv2
#import cv2
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
    areas = []

    
    for contour in pipeline.filter_contours_output:
        x, y, w, h = cv2.boundingRect(contour)
        center_x_positions.append(x + w / 2)  # X and Y are coordinates of the top-left corner of the bounding box
        center_y_positions.append(y + h / w)
        widths.append(w)
        heights.append(y)
        areas.append(w * h)

    table = NetworkTables.getTable('/vision/high_goal')
    table.putNumberArray('x', center_x_positions)
    table.putNumberArray('y', center_y_positions)
    table.putNumberArray('width', widths)
    table.putNumberArray('height', heights)
    table.putNumberArray('area', areas)


def main():
    NetworkTables.setTeam(3926)
    NetworkTables.initialize(server='roboRIO-3926-FRC.local')
    cap = cv2.VideoCapture(0)
    pipeline = GripPythonVI()
    while cap.isOpened():
        contour_number = 0
        have_frame, frame = cap.read()
        if have_frame:
            pipeline.process(frame)
            extra_processing(pipeline)
            resized_frame = cv2.resize(frame, (0, 0), fx=0.5, fy=0.5)
            (cv2.drawContours(resized_frame, pipeline.filter_contours_output,
                              -1, (255, 0, 120), thickness=-1))
            for contour in pipeline.filter_contours_output:
                x, y, w, h = cv2.boundingRect(contour)
                center = ((x+w)/2), ((y+h)/2)
                cv2.putText(resized_frame, contour_number, center)
                contour_number += 1
            cv2.imwrite('/home/pi/git/2017Season/RasberryPi/pic.jpg', resized_frame)
            if cv2.waitKey(1) & 0xFF == ord('q'):
                break

    print('Stopped Capturing')


if __name__ == '__main__':
    main()

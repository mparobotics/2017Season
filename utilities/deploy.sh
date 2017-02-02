#!/bin/bash
scp grip.pi pi@grippi.local:/home/pi/git/2017Season/RasberryPi/
ssh pi@grippi.local cd /git/2017Season/RasberryPi && workon cv && python GripPython.py
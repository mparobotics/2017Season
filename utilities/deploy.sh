#!/bin/bash
scp grip.py pi@grippi.local:/home/pi/git/2017Season/RasberryPi/
ssh pi@grippi.local sudo deploy_grip

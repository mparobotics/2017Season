#! /bin/sh
# /home/pi/git/2017season/RasberryPi/contour_streamer.sh
# symlinked to /usr/local/bin

### BEGIN INIT INFO 
# Provides: 		mjpg_streamer
# Required-Start:	$remote_fs $syslog
# Required-Stop: 	$remote_fs $syslog 
# Default-Start:	2 3 4 5
# Default-Stop: 	0 1 6
# Short-Description: 	Simple script to start mjpg stream of vision tracking 
# Description: 		A script created by Joe Brooksbank (2017) to start this Raspberry Pi with vision tracking
### END INIT INFO

case "$1" in
  start)
    echo "Starting mjpeg_streamer..."
    # run application you want to start
    /usr/local/bin/contour_streamer
    ;;
  stop)
    echo "Stopping mjpg_streamer..."
    # kill application you want to stop
    killall mjpg_streamer
    ;;
  *)
    echo "Usage: /etc/init.d/contour_streamer {start|stop}"
    exit 1
    ;;
esac

exit 0

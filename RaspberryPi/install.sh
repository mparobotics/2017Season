#!/usr/bin/env bash

if [ $EUID != 0 ]
    then echo "Not running as root, please fix"
    exit 1
fi
case "$1" in
    install)
        cp ./startscript /etc/init.d/GripRunner
        ln -s ./GripRunner.py /usr/local/bin/GripRunner
        update-rc.d GripRunner defaults

        cp ./start_stream.sh /etc/init.d/contour_streamer
        ln -s ./mjpg_streamer.sh /usr/local/bin/contour_streamer
        update-rc.d contour_streamer defaults
        ;;
    remove)
        update-rc.d -f GripRunner remove
        rm /etc/init.d/GripRunner
        rm /usr/local/bin/GripRunner

        update-rc.d -f contour_streamer remove
        rm /etc/init.d/contour_streamer
        rm /usr/local/bin/contour_streamer
        ;;
    *)
        echo "thats not an option, try install.sh {install|remove}"
        exit 1
        ;;
esac

exit 0

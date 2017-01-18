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
        ;;
    remove)
        update-rc.d -f GripRunner remove
        rm /etc/init.d/GripRunner
        rm /usr/local/bin/GripRunner
        ;;
    *)
        echo "thats not an option, try install.sh {install|remove}"
        exit 1
        ;;
esac

exit 0

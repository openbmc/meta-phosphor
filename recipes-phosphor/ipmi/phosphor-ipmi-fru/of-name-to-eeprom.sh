#!/usr/bin/env bash
[ -n ${OF_NAME+1} ] || return 0
path="$(grep -xl "$OF_NAME" /sys/bus/i2c/devices/*/of_node/name)"
eeprom="${path%/of_node/name}/eeprom"
sed -i "s,^SYSFS_PATH=.*$,SYSFS_PATH=$eeprom," "$1"

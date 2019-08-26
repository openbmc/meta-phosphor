#!/bin/sh

# Get the value of the root env variable found in /proc/cmdline
get_root() {
    local root="$(cat /proc/cmdline)"
    root="${root##* root=}"
    root="${root%% *}"
    [ "${root}" != "" ] && echo "${root}"
}

fslist="proc sys dev run"
rodir=/mnt/rofs
cd /
mkdir -p $fslist
mount dev dev -tdevtmpfs
mount sys sys -tsysfs
mount proc proc -tproc
mount tmpfs run -t tmpfs -o mode=755,nodev

# Activate available logical volumes
vgscan --mknodes
vgchange --sysinit -a ly

mkdir -p $rodir
mount "$(get_root)" $rodir -t ext4 -o ro

for f in $fslist; do
	mount --move $f $rodir/$f
done

exec chroot $rodir /sbin/init

#!/bin/sh

# Get the value of the root env variable found in /proc/cmdline
get_root() {
    local root="$(cat /proc/cmdline)"
    root="${root##* root=}"
    root="${root%% *}"
    [ "${root}" != "" ] && echo "${root}"
}

fslist="proc sys dev run"
cd /
mkdir -p $fslist
mount dev dev -tdevtmpfs
mount sys sys -tsysfs
mount proc proc -tproc
mount tmpfs run -t tmpfs -o mode=755,nodev

# Activate available logical volumes
vgscan --mknodes
vgchange --sysinit -a ly

mkdir -p /run/rofs
mount "$(get_root)" /run/rofs -t ext4 -o ro

exec chroot /run/rofs /sbin/init


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

. dm.env

dmsetup create --concise 'rootfs-a,,,rw, 0 2097152 linear /dev/mmcblk0p2 0'
dmsetup create --concise 'rootfs-b,,,rw, 0 2097152 linear /dev/mmcblk0p2 2097152'
dmsetup create --concise 'rwfs,,,rw, 0 12582912 linear /dev/mmcblk0p2 4194304'

dmsetup mknodes

mkdir -p $rodir
mount "(get_root)" $rodir -t ext4 -o ro
mount /dev/mapper/rwfs $rodir/var -t ext4 -o rw

rm -rf $rodir/var/persist/etc-work/
mkdir -p $rodir/var/persist/etc $rodir/var/persist/etc-work
mount overlay $rodir/etc -t overlay -o lowerdir=$rodir/etc,upperdir=$rodir/var/persist/etc,workdir=$rodir/var/persist/etc-work

for f in $fslist; do
    mount --move $f $rodir/$f
done

/bin/sh

exec chroot $rodir /sbin/init

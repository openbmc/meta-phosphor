#!/bin/bash

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

# Get the image layout information
source /dm.env

ROFSA_SECTORS=$(($ROFS_A_SIZE / $SECTOR_SIZE))
ROFSB_SECTORS=$(($ROFS_B_SIZE / $SECTOR_SIZE))
RWFS_SECTORS=$(($RWFS_SIZE / $SECTOR_SIZE))
OTHER_SECTORS=$(($OTHER_SIZE / $SECTOR_SIZE))
ROFSA_OFFSET_SECTORS=$(($ROFS_A_DM_OFFSET / $SECTOR_SIZE))
ROFSB_OFFSET_SECTORS=$(($ROFS_B_DM_OFFSET / $SECTOR_SIZE))
RWFS_OFFSET_SECTORS=$(($RWFS_DM_OFFSET / $SECTOR_SIZE))
OTHER_OFFSET_SECTORS=$(($OTHER_DM_OFFSET / $SECTOR_SIZE))

dmsetup create --concise "rofs-a,,,rw, 0 $ROFSA_SECTORS linear /dev/mmcblk0p2 $ROFSA_OFFSET_SECTORS"
dmsetup create --concise "rofs-b,,,rw, 0 $ROFSB_SECTORS linear /dev/mmcblk0p2 $ROFSB_OFFSET_SECTORS"
dmsetup create --concise "rwfs,,,rw, 0 $RWFS_SECTORS linear /dev/mmcblk0p2 $RWFS_OFFSET_SECTORS"
dmsetup create --concise "other,,,rw, 0 $OTHER_SECTORS linear /dev/mmcblk0p2 $OTHER_OFFSET_SECTORS"

dmsetup mknodes

# Copy the env file to user space if needed for reference
mkdir -p /run/initramfs
cp /dm.env /run/initramfs

mkdir -p $rodir
echo "root=$(get_root)"
mount "$(get_root)" $rodir -t ext4 -o ro
mount /dev/mapper/rwfs $rodir/var -t ext4 -o rw

rm -rf $rodir/var/persist/etc-work/
mkdir -p $rodir/var/persist/etc $rodir/var/persist/etc-work $rodir/var/persist/home/root
mount overlay $rodir/etc -t overlay -o lowerdir=$rodir/etc,upperdir=$rodir/var/persist/etc,workdir=$rodir/var/persist/etc-work

for f in $fslist; do
    mount --move $f $rodir/$f
done

exec chroot $rodir /sbin/init

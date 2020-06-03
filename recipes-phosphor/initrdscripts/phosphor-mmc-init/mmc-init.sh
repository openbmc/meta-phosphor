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

mkdir -p $rodir
mount "$(get_root)" $rodir -t ext4 -o ro
mount /dev/mmcblk0p6 $rodir/var -t ext4 -o rw

# If $rodir/var is empty, assume this is the first boot and issue sgdisk -e to
# move the secondary GPT to the end where it belongs. An empty ext4 filesystem
# would only have the lost+found directory.
if [ "$(ls $rodir/var)" == "lost+found" ]; then
    sgdisk -e /dev/mmcblk0
fi

rm -rf $rodir/var/persist/etc-work/
mkdir -p $rodir/var/persist/etc $rodir/var/persist/etc-work $rodir/var/persist/home/root
mount overlay $rodir/etc -t overlay -o lowerdir=$rodir/etc,upperdir=$rodir/var/persist/etc,workdir=$rodir/var/persist/etc-work

for f in $fslist; do
    mount --move $f $rodir/$f
done

exec chroot $rodir /sbin/init

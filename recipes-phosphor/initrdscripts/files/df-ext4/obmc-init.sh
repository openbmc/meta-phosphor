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

# Check if read-write fs exists, if not it means this is a newly flashed emmc
# that contains only the kernel and rootfs.
if [ ! -e "/dev/mapper/vg-rwfs" ]; then
    # Create partition #3 (#1:kernel, #2:rootfs) for lvm (use all remaining
    # space in the emmc) and create the rwfs volume.
    fdisk /dev/mmcblk0 << HERE
    n
    p
    3


    w
HERE

    pvcreate /dev/mmcblk0p3
    vgcreate vg /dev/mmcblk0p3
    lvcreate -L 2G -n rwfs vg
    mkfs.ext4 /dev/mapper/vg-rwfs
fi

mkdir -p $rodir
mount "$(get_root)" $rodir -t ext4 -o ro

mount /dev/mapper/vg-rwfs $rodir/var -o rw
rm -rf $rodir/var/persist/etc-work/
mkdir -p $rodir/var/persist/etc $rodir/var/persist/etc-work
mount overlay $rodir/etc -t overlay -o lowerdir=$rodir/etc,upperdir=$rodir/var/persist/etc,workdir=$rodir/var/persist/etc-work

for f in $fslist; do
	mount --move $f $rodir/$f
done

exec chroot $rodir /sbin/init

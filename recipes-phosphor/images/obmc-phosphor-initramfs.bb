DESCRIPTION = "Small image capable of booting a device. The kernel includes \
the Minimal RAM-based Initial Root Filesystem (initramfs), which finds the \
first 'init' program more efficiently."

# Init scripts
INIT_PACKAGE = "obmc-phosphor-initfs"
INIT_PACKAGE_df-phosphor-mmc = "phosphor-mmc-init"

PACKAGE_INSTALL = "${VIRTUAL-RUNTIME_base-utils} base-passwd ${ROOTFS_BOOTSTRAP_INSTALL} ${INIT_PACKAGE}"

# Do not pollute the initrd image with rootfs features
IMAGE_FEATURES = "read-only-rootfs"

export IMAGE_BASENAME = "obmc-phosphor-initramfs"
IMAGE_LINGUAS = ""

LICENSE = "MIT"

IMAGE_FSTYPES = "${INITRAMFS_FSTYPES}"
inherit core-image

IMAGE_ROOTFS_SIZE = "8192"
IMAGE_ROOTFS_EXTRA_SPACE = "0"

# Needed for the set_user_group functions to succeed
DEPENDS += "shadow-native"

PACKAGE_INSTALL_remove = "shadow"
PACKAGE_EXCLUDE = "shadow"
BAD_RECOMMENDATIONS += "busybox-syslog"

# Depends on building the eMMC env file containing the image layout information
do_rootfs[depends] += '${@bb.utils.contains("DISTRO_FEATURES", "phosphor-mmc", "obmc-phosphor-image:do_generate_mmc_ext4", "", d)}'
deploy_dm_env_file() {
    install -D -m 0644 ${DEPLOY_DIR_IMAGE}/obmc-phosphor-image-${MACHINE}.dm.env ${IMAGE_ROOTFS}/dm.env
}
ROOTFS_POSTPROCESS_COMMAND += '${@bb.utils.contains("DISTRO_FEATURES", "phosphor-mmc", "deploy_dm_env_file;", "", d)}'

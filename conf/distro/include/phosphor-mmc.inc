DISTRO_FEATURES += "phosphor-mmc"
DISTROOVERRIDES .= ":df-phosphor-mmc"

# Install the kernel in the rootfs in the /boot directory
IMAGE_INSTALL_append = " kernel-image"

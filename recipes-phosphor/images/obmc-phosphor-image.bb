DESCRIPTION = "Image with Phosphor, a software stack for hardware management \
in devices with baseboard management controllers.  The image supports the \
full OpenBMC feature set for devices of all types."

IMAGE_LINGUAS = ""

IMAGE_FEATURES += " \
        obmc-bmc-state-mgmt \
        obmc-chassis-mgmt \
        obmc-chassis-state-mgmt \
        obmc-fan-control \
        obmc-fan-mgmt \
        obmc-flash-mgmt \
        obmc-host-ctl \
        obmc-host-ipmi \
        obmc-host-state-mgmt \
        obmc-inventory \
        obmc-leds \
        obmc-logging-mgmt \
        obmc-remote-logging-mgmt \
        obmc-net-ipmi \
        obmc-sensors \
        obmc-software \
        obmc-system-mgmt \
        ${@bb.utils.contains('DISTRO_FEATURES', 'obmc-ubi-fs', 'read-only-rootfs', '', d)} \
        obmc-user-mgmt \
        ssh-server-dropbear \
        obmc-debug-collector \
        obmc-network-mgmt \
        obmc-settings-mgmt \
        ${@bb.utils.contains('DISTRO_FEATURES', 'phosphor-mmc', 'read-only-rootfs', '', d)} \
        "

inherit obmc-phosphor-image

SUMMARY = "Phosphor OpenBMC IPMB to DBUS"
DESCRIPTION = "Phosphor OpenBMC IPMB to DBUS."
PR = "r1"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e3fc50a88d0a364313df4b21ef20c29e"

inherit autotools pkgconfig
inherit obmc-phosphor-dbus-service

DBUS_SERVICE_${PN} = "xyz.openbmc_project.Ipmi.Channel.Ipmb.service"

DEPENDS += " \
        autoconf-archive-native \
        systemd \
        sdbusplus \
        phosphor-logging \
        i2c-tools \
        boost \
        "
RDEPENDS_${PN} += " \
        libsystemd \
        "

S = "${WORKDIR}/git"
SRC_URI = "git://github.com:openbmc/ipmbbridge.git;nobranch=1"
SRCREV = "4bf1cf7a81c40674dce2d3784da67c488e5eefc0"

do_configure[depends] += "virtual/kernel:do_shared_workdir"

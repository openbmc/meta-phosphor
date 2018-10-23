SUMMARY = "Phosphor OpenBMC IPMB to DBUS"
DESCRIPTION = "Phosphor OpenBMC IPMB to DBUS."
PV = "1.0+git${SRCPV}"

LICENSE = "Apache-2.0"
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

S = "${WORKDIR}/git"
SRC_URI = "git://git-amr-2.devtools.intel.com:29418/openbmc-ipmb;protocol=ssh"
SRCREV = "254196a45f231fd15ec056005599d94c184cb4da"
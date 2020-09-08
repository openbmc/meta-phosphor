SUMMARY = "Phosphor DBus Interfaces"
DESCRIPTION = "Generated bindings, using sdbus++, for the phosphor YAML"
PR = "r1"
PV = "1.0+git${SRCPV}"
S = "${WORKDIR}/git"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e3fc50a88d0a364313df4b21ef20c29e"

inherit autotools pkgconfig
inherit python3native
inherit phosphor-dbus-yaml

DEPENDS += " \
        ${PYTHON_PN}-sdbus++-native \
        autoconf-archive-native \
        sdbusplus \
        systemd \
        "

SRC_URI = "git://github.com/openbmc/phosphor-dbus-interfaces"
SRCREV = "1751787a076983b8d75bb208597272482e4c657b"

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

DEPENDS += "autoconf-archive-native"
DEPENDS += "${PYTHON_PN}-sdbus++-native"

SRC_URI = "git://github.com/openbmc/phosphor-dbus-interfaces"
SRCREV = "264d401d288841da24b9ce1377e3c399a2f4c60a"

PACKAGECONFIG ??= "libphosphor_dbus"
PACKAGECONFIG[libphosphor_dbus] = " \
        --enable-libphosphor_dbus, \
        --disable-libphosphor_dbus, \
        systemd sdbusplus, \
        libsystemd \
        "

PACKAGECONFIG_remove_class-native = "libphosphor_dbus"
PACKAGECONFIG_remove_class-nativesdk = "libphosphor_dbus"

BBCLASSEXTEND += "native nativesdk"

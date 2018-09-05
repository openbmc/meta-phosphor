SUMMARY = "Network DBUS object"
DESCRIPTION = "Network DBUS object"
HOMEPAGE = "http://github.com/openbmc/phosphor-networkd"
PR = "r1"

inherit autotools pkgconfig
inherit pythonnative
inherit obmc-phosphor-license
inherit obmc-phosphor-dbus-service
inherit phosphor-networkd-rev

DBUS_SERVICE_${PN} += "xyz.openbmc_project.Network.service"

DEPENDS += "systemd"
DEPENDS += "autoconf-archive-native"
DEPENDS += "sdbusplus sdbusplus-native"
DEPENDS += "phosphor-dbus-interfaces phosphor-dbus-interfaces-native"
DEPENDS += "phosphor-logging"
DEPENDS += "libnl"

RDEPENDS_${PN} += "libsystemd"
RDEPENDS_${PN} += "sdbusplus phosphor-dbus-interfaces"
RDEPENDS_${PN} += "phosphor-logging"
RDEPENDS_${PN} += "libnl"
RDEPENDS_${PN} += "libnl-genl"

S = "${WORKDIR}/git"

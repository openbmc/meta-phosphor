SUMMARY = "SNMP Manager Configuration"
DESCRIPTION = "SNMP Manager Configuration."
HOMEPAGE = "http://github.com/openbmc/phosphor-snmp"
PR = "r1"
PV = "0.1+git${SRCPV}"

inherit autotools pkgconfig
inherit pythonnative
inherit obmc-phosphor-dbus-service

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

SRC_URI += "git://github.com/openbmc/phosphor-snmp"
SRCREV = "7f1c6e1d2e0f3d149574edb610b303aa26bc2078"

DBUS_SERVICE_${PN} += "xyz.openbmc_project.Network.SNMP.service"

DEPENDS += "systemd"
DEPENDS += "autoconf-archive-native"
DEPENDS += "sdbusplus sdbusplus-native"
DEPENDS += "phosphor-dbus-interfaces phosphor-dbus-interfaces-native"
DEPENDS += "phosphor-logging"
DEPENDS += "net-snmp"

RDEPENDS_${PN} += "libsystemd"
RDEPENDS_${PN} += "sdbusplus phosphor-dbus-interfaces"
RDEPENDS_${PN} += "phosphor-logging"
RDEPENDS_${PN} += "net-snmp-lib-netsnmp"

S = "${WORKDIR}/git"

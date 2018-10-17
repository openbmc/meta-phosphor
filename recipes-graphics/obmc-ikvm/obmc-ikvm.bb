SUMMARY = "OpenBMC VNC server and ipKVM daemon"
DESCRIPTION = "obmc-ikvm is a vncserver for the AST2XXX video engine to allow ipKVM"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e3fc50a88d0a364313df4b21ef20c29e"

DEPENDS = " libvncserver sdbusplus sdbusplus-native phosphor-logging phosphor-dbus-interfaces autoconf-archive-native"
RDEPENDS_${PN} += "libvncserver sdbusplus phosphor-logging phosphor-dbus-interfaces"

SRC_URI = "git://github.com/openbmc/obmc-ikvm"
SRCREV = "dd2fb3d817585f70b50456f50b5782b82b26223d"

SRC_URI += " file://0001-Add-obmc-ikvm-application.patch"
SRC_URI += " file://0002-Add-script-to-instantiate-USB-HID-gadget-device.patch"

PR = "r1"
PR_append = "+gitr${SRCPV}"

S = "${WORKDIR}/git"

inherit autotools pkgconfig

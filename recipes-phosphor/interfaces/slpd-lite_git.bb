SUMMARY = "Lightweight SLP Server"
DESCRIPTION = "Lightweight Unicast-only SLP Server"
HOMEPAGE = "http://github.com/openbmc/slpd-lite"
PR = "r1"
PV = "1.0+git${SRCPV}"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e3fc50a88d0a364313df4b21ef20c29e"

inherit autotools pkgconfig
inherit obmc-phosphor-systemd

SYSTEMD_SERVICE_${PN} += "slpd-lite.service"

DEPENDS += "systemd"
DEPENDS += "autoconf-archive-native"

SRC_URI += "git://github.com/openbmc/slpd-lite"

SRCREV = "1f12e3805a69e8f7e5dada4b40e5c0860938a227"

S = "${WORKDIR}/git"

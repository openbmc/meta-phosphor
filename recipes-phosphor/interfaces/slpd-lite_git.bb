SUMMARY = "Lightweight SLP Server"
DESCRIPTION = "Lightweight Unicast-only SLP Server"
HOMEPAGE = "http://github.com/openbmc/slpd-lite"
PR = "r1"
PV = "1.0+git${SRCPV}"

inherit autotools pkgconfig
inherit obmc-phosphor-license
inherit obmc-phosphor-systemd

SYSTEMD_SERVICE_${PN} += "slpd-lite.service"

DEPENDS += "systemd"
DEPENDS += "autoconf-archive-native"

SRC_URI += "git://github.com/openbmc/slpd-lite"

SRCREV = "537ff140250973ac20dcd392f5b1610637a7d734"

S = "${WORKDIR}/git"

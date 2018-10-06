SUMMARY = "Phosphor Network IPMI Daemon"
DESCRIPTION = "Daemon to support IPMI protocol over network"
HOMEPAGE = "https://github.com/openbmc/phosphor-net-ipmid"
PR = "r1"

inherit autotools pkgconfig
inherit obmc-phosphor-license
inherit obmc-phosphor-systemd

DEPENDS += "autoconf-archive-native"
DEPENDS += "phosphor-mapper"
DEPENDS += "systemd"
DEPENDS += "phosphor-ipmi-host"
RDEPENDS_${PN} += "libmapper"
RDEPENDS_${PN} += "libsystemd"
RDEPENDS_${PN} += "iptables"

SRC_URI += "git://github.com/openbmc/phosphor-net-ipmid"
SRCREV = "5e452093830644245b83a817b8dad2fdb36ea550"

S = "${WORKDIR}/git"

SYSTEMD_SERVICE_${PN} = " \
        ${PN}.service \
        ${PN}.socket \
        "

SUMMARY = "Phosphor ipmi tool for injecting ipmi commands"
DESCRIPTION = "IPMI Tool with dbus capabilities"
HOMEPAGE = "https://github.com/openbmc/ipmitool"
PR = "r1"
PV = "1.0+git${SRCPV}"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://COPYING;md5=9aa91e13d644326bf281924212862184"

DEPENDS += "systemd    \
            phosphor-ipmi-host \
            "

RDEPENDS_${PN} += "libsystemd \
                   libcrypto \
                   virtual-obmc-host-ipmi-hw \
                   "


SRC_URI += "git://github.com/openbmc/ipmitool"

SRCREV = "34fc86bd751f29a342c8568357bea2deb4cb8870"


S = "${WORKDIR}/git"


do_compile() {
        ${S}/bootstrap --enable-intf-dbus
        ${S}/configure --host x86_64
        make
}

do_install() {
        install -m 0755 -d ${D}${sbindir}
        install -m 0755 ${S}/src/ipmitool ${D}${sbindir}
}

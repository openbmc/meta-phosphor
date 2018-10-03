SUMMARY = "Network Block Device"
HOMEPAGE = "https://github.com/NetworkBlockDevice/nbd"
PR = "r1"
PV = "1.0+git${SRCPV}"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

inherit autotools pkgconfig

DEPENDS += "glib-2.0"

PACKAGECONFIG[manpages] = "--enable-manpages,--disable-manpages"

S = "${WORKDIR}/git"

SRC_URI = "git://github.com/NetworkBlockDevice/nbd.git"
SRCREV = "712442dd63e605552334656f1d93eb722e8c57a7"

do_configure_prepend() {
    cd ${S}
    make -C systemd -f Makefile.am nbd@.service.sh.in
    cd -
}

do_install() {
    install -d ${D}${sbindir}
    install -m 0755 ${WORKDIR}/build/nbd-client ${D}${sbindir}/nbd-client
}

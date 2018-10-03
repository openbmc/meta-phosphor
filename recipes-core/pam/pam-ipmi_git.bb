SUMMARY = "PAM modules for IPMI support"
DESCRIPTION = "PAM modules managing password for IPMI"
HOMEPAGE = "http://github.com/openbmc/pam-ipmi"
PR = "r1"
PV = "1.0+git${SRCPV}"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

SRC_URI += "git://github.com/openbmc/pam-ipmi"
SRCREV = "216f2139a4ab302e8cb2d3b85ff87b4747db7b60"
S = "${WORKDIR}/git"

inherit autotools pkgconfig

DEPENDS += "openssl libpam"

FILES_${PN} += " \
               ${base_libdir}/security/ \
               ${sysconfdir}/key_file \
               "

do_install_append() {
    install -d ${D}${sysconfdir}
    install -m 0400 ${S}/key_file ${D}${sysconfdir}/
}

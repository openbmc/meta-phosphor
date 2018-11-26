SUMMARY = "PAM modules for IPMI support"
DESCRIPTION = "PAM modules managing password for IPMI"
HOMEPAGE = "http://github.com/openbmc/pam-ipmi"
PR = "r1"
PV = "1.0+git${SRCPV}"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

SRC_URI += "git://github.com/openbmc/pam-ipmi"
SRCREV = "bca65eef2c68cad04baaea7c6f5a3a66a17dee9e"
S = "${WORKDIR}/git"

inherit autotools pkgconfig

DEPENDS += "autoconf-archive-native"
DEPENDS += "openssl libpam"

FILES_${PN} += " \
               ${base_libdir}/security/ \
               ${sysconfdir}/key_file \
               "

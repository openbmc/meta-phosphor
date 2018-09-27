HOMEPAGE = "http://github.com/openbmc/phosphor-ipmi-blobs"
SUMMARY = "Phosphor OEM IPMI BLOBS Protocol Implementation"
DESCRIPTION = "This package handles a series of OEM IPMI commands that implement the BLOB protocol for sending and receiving data over IPMI."
PR = "r1"
PV = "0.1+git${SRCPV}"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

inherit autotools pkgconfig
inherit obmc-phosphor-ipmiprovider-symlink

DEPENDS += "autoconf-archive-native"
DEPENDS += "phosphor-ipmi-host"

S = "${WORKDIR}/git"
SRC_URI = "git://github.com/openbmc/phosphor-ipmi-blobs"
SRCREV = "e01c0af722d342962dd57c4de7a610bf0dbb3e6a"

FILES_${PN}_append = " ${libdir}/ipmid-providers/lib*${SOLIBS}"
FILES_${PN}_append = " ${libdir}/host-ipmid/lib*${SOLIBS}"
FILES_${PN}_append = " ${libdir}/net-ipmid/lib*${SOLIBS}"
FILES_${PN}-dev_append = " ${libdir}/ipmid-providers/lib*${SOLIBSDEV} ${libdir}/ipmid-providers/*.la"

HOSTIPMI_PROVIDER_LIBRARY += "libblobcmds.so"

DESCRIPTION = "libvncserver - A library implementing the VCN protocol"
HOMEPAGE = "https://github.com/LibVNC/libvncserver"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=361b6b837cad26c6900a926b62aada5f"

PR = "r1"
PR_append = "+gitr${SRCPV}"

DEPENDS += "jpeg openssl zlib"
SRC_URI = "git://github.com/LibVNC/libvncserver"
SRCREV = "e611616723035d2345cf27e43c8b2eef958d5696"

S = "${WORKDIR}/git"

TARGET_CXXFLAGS += " -Dflto"
EXTRA_OECMAKE += " -DWITH_PNG=OFF"

inherit cmake

do_install_append() {
    rm -rf ${D}${libdir}/libvncclient*
}

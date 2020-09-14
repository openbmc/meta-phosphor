SUMMARY     = "Microsemi Switchtec Debug Tool"
DESCRIPTION = "switchtec provides a command line interface for a variety of debug commands"
LICENSE     = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI += "git://github.com/Microsemi/switchtec-user.git"
SRCREV = "v2.4-rc3"

DEPENDS += "ncurses"
DEPENDS += "openssl"

S = "${WORKDIR}/git"

inherit autotools

BBCLASSEXTEND = "native"

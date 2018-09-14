SUMMARY = "C++11 command line parser"
DESCRIPTION = "A command line parser for C++11 and beyond that provides a rich feature set with a simple and intuitive interface."
HOMEPAGE = "https://github.com/CLIUtils/CLI11"
PR = "r1"
PV = "1.6.1"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c271fee3ae28e11b24b97284d9f82887"

inherit cmake

EXTRA_OECMAKE_append += " \
        -DCLI11_TESTING=OFF \
        -DCLI11_EXAMPLES=OFF \
        "

SRC_URI += "git://github.com/CLIUtils/CLI11"
SRCREV = "v${PV}"

S = "${WORKDIR}/git"

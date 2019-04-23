LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=fa818a259cbed7ce8bc2a22d35a464fc"

SRC_URI = "git://git@github.com/edtanous/libmctp.git;branch=pr1;protocol=ssh"

PV = "1.0+git${SRCPV}"
SRCREV = "853a87e1b6b2b14e390c94b4e98c4493159eceb1"
DEPENDS += "i2c-tools"

S = "${WORKDIR}/git"

inherit cmake


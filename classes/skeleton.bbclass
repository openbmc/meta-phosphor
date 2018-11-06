inherit skeleton-rev

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${PHOSPHORBASE}/COPYING.apache-2.0;md5=34400b68072d710fecd0a2940a0d1658"

HOMEPAGE = "http://github.com/openbmc/skeleton"

SRC_URI += "${SKELETON_URI}"
S = "${WORKDIR}/git/${SKELETON_DIR}"

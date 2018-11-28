SUMMARY = "Init BMC MAC address"
DESCRIPTION = "Setup BMC MAC address from readed from VPD"
PR = "r1"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${PHOSPHORBASE}/COPYING.apache-2.0;md5=34400b68072d710fecd0a2940a0d1658"

inherit obmc-phosphor-systemd

do_patch[noexec] = "1"
do_configure[noexec] = "1"
do_compile[noexec] = "1"

RDEPENDS_${PN} = "bash"

SYSTEMD_SERVICE_${PN} = "bmc-first-init.service"
BMC_NETWORK_ITEM_PATH ?= "/system/chassis/ethernet"

S = "${WORKDIR}"
SRC_URI += "file://bmc-first-init.sh"

do_install() {
    install -d ${D}${sbindir}
    install -m 0755 ${S}/bmc-first-init.sh ${D}${sbindir}/
    sed -i "s|{bmc_network_item_path}|${BMC_NETWORK_ITEM_PATH}|g" \
        ${D}${sbindir}/bmc-first-init.sh
}

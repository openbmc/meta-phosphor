FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
    file://nslcd-cert.conf \
    file://gen-nslcd-cert.sh \
    "
NSLCD_CERT_PATH = "/etc/nslcd/certs/"

FILES_${PN} += "${systemd_unitdir}/system/nslcd.service.d/nslcd-cert.conf"

FILES_${PN} += " ${NSLCD_CERT_PATH} "

do_install_append() {
        install -m 644 -D ${WORKDIR}/nslcd-cert.conf ${D}${systemd_unitdir}/system/nslcd.service.d/nslcd-cert.conf
        install -m 0755 ${WORKDIR}/gen-nslcd-cert.sh ${D}${sbindir}/gen-nslcd-cert.sh
        install -d ${D}${NSLCD_CERT_PATH}
        chmod -R 644 ${D}${NSLCD_CERT_PATH}
}

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
    file://nslcd.conf \
    file://gen-nslcd-cert.sh \
    "
NSLCD_CERT_PATH = "/etc/nslcd/certs/"

FILES_${PN} += "${systemd_unitdir}/nslcd.conf.d/nslcd.conf"

FILES_${PN} += " ${NSLCD_CERT_PATH} "

do_install_append() {
        install -m 644 -D ${WORKDIR}/nslcd.conf ${D}${systemd_unitdir}/nslcd.conf.d/nslcd.conf
        install -m 0755 ${WORKDIR}/gen-nslcd-cert.sh ${D}${sbindir}/gen-nslcd-cert.sh
        install -d ${D}${NSLCD_CERT_PATH}
        chmod -R 777 ${D}${NSLCD_CERT_PATH}
}

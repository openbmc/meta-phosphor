SUMMARY = "Redirect HTTP to HTTPS"
PR = "r1"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

inherit systemd
inherit obmc-phosphor-systemd

RDEPENDS_${PN} += " \
        bash \
"

SYSTEMD_SERVICE_${PN} = "http-redirection.service"

SRC_URI = "file://${BPN}.sh \
           "

S = "${WORKDIR}"
do_install() {
    install -d ${D}${bindir}
    install -m 755 ${BPN}.sh ${D}${bindir}/
}

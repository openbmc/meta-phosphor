SUMMARY = "Phosphor certificate manager configuration for a bmcweb service"

PR = "r1"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

RDEPENDS_${PN} = "phosphor-certificate-manager"

inherit allarch \
        obmc-phosphor-dbus-service \
        obmc-phosphor-systemd

SRC_URI = " \
    file://bmcweb \
    file://xyz.openbmc_project.Certs.Manager.Server.Https.conf \
    "

FILESEXTRAPATHS_prepend := "${THISDIR}/phosphor-certificate-manager:"
envfiledir = "${datadir}/phosphor-certificate-manager"

TMPL = "phosphor-certificate-manager@.service"
INST = "phosphor-certificate-manager@bmcweb.service"
SYSTEMD_SERVICE_${PN} += "${TMPL}"
SYSTEMD_LINK_${PN} += "../${TMPL}:multi-user.target.wants/${INST}"
SYSTEMD_ENVIRONMENT_FILE_${PN} = "bmcweb"

DBUS_PACKAGES = "${PN}"
_INSTALL_DBUS_CONFIGS = "xyz.openbmc_project.Certs.Manager.Server.Https.conf"

FILES_${PN} = "${sysconfdir} ${datadir}"

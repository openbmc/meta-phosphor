SUMMARY = "Phosphor certificate manager configuration for a nslcd certificate"

PR = "r1"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

RDEPENDS_${PN} = "phosphor-certificate-manager"

inherit allarch \
        obmc-phosphor-dbus-service \
        obmc-phosphor-systemd

SRC_URI = "file://nslcd file://xyz.openbmc_project.Certs.Manager.Client.Ldap.conf"

FILESEXTRAPATHS_prepend := "${THISDIR}/phosphor-certificate-manager:"
envfiledir = "${datadir}/phosphor-certificate-manager"

TMPL = "phosphor-certificate-manager@.service"
INST = "phosphor-certificate-manager@nslcd.service"
SYSTEMD_SERVICE_${PN} += "${TMPL}"
SYSTEMD_LINK_${PN} += "../${TMPL}:multi-user.target.wants/${INST}"
SYSTEMD_ENVIRONMENT_FILE_${PN} = "nslcd"

_INSTALL_DBUS_CONFIGS = "xyz.openbmc_project.Certs.Manager.Client.Ldap.conf"

FILES_${PN} = "${sysconfdir} ${datadir}"

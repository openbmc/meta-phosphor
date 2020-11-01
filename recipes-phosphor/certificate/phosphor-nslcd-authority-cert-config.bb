SUMMARY = "Phosphor certificate manager configuration for an nslcd authority service"

PR = "r1"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

RDEPENDS_${PN} = "phosphor-certificate-manager"

inherit allarch \
        obmc-phosphor-dbus-service \
        obmc-phosphor-systemd

SRC_URI = "\
    file://authority \
    file://override.conf \
    file://xyz.openbmc_project.Certs.Manager.Authority.Ldap.conf \
    "

FILESEXTRAPATHS_prepend := "${THISDIR}/phosphor-certificate-manager:"
envfiledir = "${datadir}/phosphor-certificate-manager"

TMPL = "phosphor-certificate-manager@.service"
INST = "phosphor-certificate-manager@authority.service"
SYSTEMD_SERVICE_${PN} += "${TMPL}"
SYSTEMD_LINK_${PN} += "../${TMPL}:multi-user.target.wants/${INST}"
SYSTEMD_ENVIRONMENT_FILE_${PN} = "authority"
SYSTEMD_OVERRIDE_${PN} = "override.conf:${INST}.d/override.conf"

_INSTALL_DBUS_CONFIGS = "xyz.openbmc_project.Certs.Manager.Authority.Ldap.conf"

FILES_${PN} = "${sysconfdir} ${datadir}"

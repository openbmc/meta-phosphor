SUMMARY = "Phosphor Certificate Manager"
DESCRIPTION = "Manager client and server certificates"
HOMEPAGE = "https://github.com/openbmc/phosphor-certificate-manager"

PR = "r1"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

SRC_URI += "git://github.com/openbmc/phosphor-certificate-manager"
SRCREV = "6bcb52b3a7b24f36306a249365fc703322192c20"

inherit autotools \
        pkgconfig \
        obmc-phosphor-systemd

DEPENDS += " \
        phosphor-logging \
        autoconf-archive-native \
        phosphor-dbus-interfaces \
        phosphor-dbus-interfaces-native \
        sdbusplus \
        sdbusplus-native \
        "

RDEPENDS_${PN} += " \
        phosphor-logging \
        phosphor-dbus-interfaces \
        sdbusplus \
        "

FILES_${PN} += "${sbindir}/phosphor_certificate_manager"

S = "${WORKDIR}/git"

CERT_TMPL = "phosphor-certificate-manager@.service"
SYSTEMD_SERVICE_${PN} += "${CERT_TMPL}"

SYSTEMD_ENVIRONMENT_FILE_${PN} += "obmc/cert/ldap"

SYSD_TGT = "${SYSTEMD_DEFAULT_TARGET}"
CERTS = "ldap"
CERT_TGTFMT = "phosphor-certificate-manager@{0}.service"
CERT_FMT = "../${CERT_TMPL}:${SYSD_TGT}.wants/${CERT_TGTFMT}"
SYSTEMD_LINK_${PN} += "${@compose_list(d, 'CERT_FMT', 'CERTS')}"

SUMMARY = "Phosphor Certificate Manager"
DESCRIPTION = "Manager client and server certificates"
HOMEPAGE = "https://github.com/openbmc/phosphor-certificate-manager"

PR = "r1"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

SRC_URI += "git://github.com/openbmc/phosphor-certificate-manager"
SRCREV = "47ccaa530f8821c4bd2c3841693054edafb3f743"

inherit autotools \
        pkgconfig \
        obmc-phosphor-systemd

CERT_PACKAGES += " \
        phosphor-cert-ldap  \
        "

PACKAGE_BEFORE_PN += "${CERT_PACKAGES}"
ALLOW_EMPTY_${PN} = "1"
SYSTEMD_PACKAGES = "${CERT_PACKAGES}"

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
SYSTEMD_SERVICE_phosphor-cert-ldap += "${CERT_TMPL}"

SYSTEMD_ENVIRONMENT_FILE_phosphor-cert-ldap += "obmc/cert/ldap"

SYSD_TGT = "${SYSTEMD_DEFAULT_TARGET}"
CERTS = "ldap"
CERT_TGTFMT = "phosphor-certificate-manager@{0}.service"
CERT_FMT = "../${CERT_TMPL}:${SYSD_TGT}.wants/${CERT_TGTFMT}"
SYSTEMD_LINK_phosphor-cert-ldap += "${@compose_list(d, 'CERT_FMT', 'CERTS')}"

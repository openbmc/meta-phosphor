SUMMARY = "Phosphor Certificate Manager"
DESCRIPTION = "Certificate manager for LDAP and HTTPS instances"
HOMEPAGE = "https://github.com/openbmc/phosphor-certificate-manager"

PR = "r1"
PV = "0.1+git${SRCPV}"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

SRC_URI += "git:////esw/san5/devenrao/obmc/phosphor-certificate-manager"
SRCREV = "9c869f1e38f587c74a514441a9bc525e00d75468"

inherit autotools \
        pkgconfig \
        obmc-phosphor-systemd

DEPENDS += " \
        phosphor-logging \
        autoconf-archive-native \
        "

RDEPENDS_${PN} += " \
            phosphor-logging \
            "

FILES_${PN} += "${sbindir}/phosphor_certificate_manager"

S = "${WORKDIR}/git"

SYSTEMD_ENVIRONMENT_FILE_${PN} += "obmc/cert/https"
SYSTEMD_ENVIRONMENT_FILE_${PN} += "obmc/cert/ldap"

#create 2 instances of certificate manager
SYSD_TGT = "${SYSTEMD_DEFAULT_TARGET}"
CERT_TMPL = "phosphor-certificate-manager@.service"
CERTS = "ldap https"
CERT_TGTFMT = "phosphor-certificate-manager@{0}.service"
CERT_FMT = "../${CERT_TMPL}:${SYSD_TGT}.wants/${CERT_TGTFMT}"

SYSTEMD_SERVICE_${PN} += "${CERT_TMPL}"
SYSTEMD_LINK_${PN} += "${@compose_list(d, 'CERT_FMT', 'CERTS')}"


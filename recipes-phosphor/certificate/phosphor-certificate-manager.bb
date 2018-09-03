SUMMARY = "Phosphor Certificate Manager"
DESCRIPTION = "Manager client and server certificates"
HOMEPAGE = "https://github.com/openbmc/phosphor-certificate-manager"

PR = "r1"
PV = "0.1+git${SRCPV}"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

SRC_URI += "git:////esw/san5/devenrao/obmc/phosphor-certificate-manager"
SRCREV = "ec577baa3eab94545f09341c3972b9c8a1cd9377"

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

SYSTEMD_ENVIRONMENT_FILE_${PN} += "obmc/cert/ldap"
SYSTEMD_SERVICE_${PN} += "phosphor-certificate-manager-ldap.service" 

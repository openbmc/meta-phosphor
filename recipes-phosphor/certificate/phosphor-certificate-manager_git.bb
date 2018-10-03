SUMMARY = "Phosphor Certificate Manager"
DESCRIPTION = "Manages client and server certificates"
HOMEPAGE = "https://github.com/openbmc/phosphor-certificate-manager"

PR = "r1"
PV = "0.1+git${SRCPV}"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

SRC_URI = "git://github.com/openbmc/phosphor-certificate-manager"
SRCREV = "cfbc8dc8904bfdc1776e1a9f841fb48d00d6d968"

inherit autotools \
        pkgconfig \
        obmc-phosphor-systemd

DEPENDS = " \
        phosphor-logging \
        autoconf-archive-native \
        phosphor-dbus-interfaces \
        phosphor-dbus-interfaces-native \
        sdbusplus \
        sdbusplus-native \
        "

S = "${WORKDIR}/git"

FILES_${PN} += "${exec_prefix}/lib/tmpfiles.d/certs.conf"
SRC_URI += "file://certs.conf"

do_install_append() {
    # /tmp/cert_*.pem is the temporary certificate file.
    # It should not be deleted since it is used by certificate manager
    # for validation process before moving to permanent location.

    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true', 'false', d)}; then
        install -d ${D}${exec_prefix}/lib/tmpfiles.d
        install -m 644 ${WORKDIR}/certs.conf ${D}${exec_prefix}/lib/tmpfiles.d/
    fi
}


CERT_TMPL = "phosphor-certificate-manager@.service"
SYSTEMD_SERVICE_${PN} = "${CERT_TMPL}"

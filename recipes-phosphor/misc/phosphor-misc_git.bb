SUMMARY = "Miscellaneous OpenBMC functions"
HOMEPAGE = "https://github.com/openbmc/phosphor-misc"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e3fc50a88d0a364313df4b21ef20c29e"
PR = "r1"
PV = "1.0+git${SRCPV}"

SRC_URI += "git://github.com/openbmc/phosphor-misc"
SRCREV = "756eaf798dc804fc7936ff1121c6f3e441d29fac"

PACKAGECONFIG[first_boot_set_mac] = "-Dfirst-boot-set-mac=enabled, -Dfirst-boot-set-mac=disabled"
PACKAGECONFIG[first_boot_set_hostname] = "-Dfirst-boot-set-hostname=enabled, -Dfirst-boot-set-hostname=disabled"
PACKAGECONFIG[http_redirect] = "-Dhttp-redirect=enabled, -Dhttp-redirect=disabled"

inherit meson
inherit systemd

DEPENDS += "systemd"

RDEPENDS_${PN} += " \
    ${@bb.utils.contains('PACKAGECONFIG', 'http_redirect', 'gawk', '', d)} \
    "

FILES_${PN} += " \
        ${@bb.utils.contains('PACKAGECONFIG', 'first_boot_set_mac', '${bindir}/first-boot-set-mac.sh', '', d)} \
        ${@bb.utils.contains('PACKAGECONFIG', 'first_boot_set_hostname', '${bindir}/first-boot-set-hostname.sh', '', d)} \
        ${@bb.utils.contains('PACKAGECONFIG', 'http_redirect', '${bindir}/http-redirect.awk', '', d)} \
    "

SYSTEMD_SERVICE_${PN} += " \
    ${@bb.utils.contains('PACKAGECONFIG', 'first_boot_set_mac', 'first-boot-set-mac@.service', '', d)} \
    ${@bb.utils.contains('PACKAGECONFIG', 'first_boot_set_hostname', 'first-boot-set-hostname.service', '', d)} \
    ${@bb.utils.contains('PACKAGECONFIG', 'http_redirect', 'http-redirct@.service http-redirct.socket', '', d)} \
    "

S = "${WORKDIR}/git"

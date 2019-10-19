SUMMARY = "Phosphor GPIO monitor application"
DESCRIPTION = "Application to monitor gpio assertions"
HOMEPAGE = "http://github.com/openbmc/phosphor-gpio-monitor"
PR = "r1"
PV = "1.0+git${SRCPV}"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e3fc50a88d0a364313df4b21ef20c29e"
inherit autotools pkgconfig
inherit obmc-phosphor-dbus-service

GPIO_PACKAGES = " \
        ${PN}-monitor \
        ${PN}-presence \
"

PACKAGE_BEFORE_PN += "${GPIO_PACKAGES}"
ALLOW_EMPTY_${PN} = "1"
SYSTEMD_PACKAGES = "${GPIO_PACKAGES}"

RPROVIDES_${PN}-monitor += "virtual/obmc-gpio-monitor"
RPROVIDES_${PN}-presence += "virtual/obmc-gpio-presence"

PROVIDES += "virtual/obmc-gpio-monitor"
PROVIDES += "virtual/obmc-gpio-presence"

DEPENDS += "autoconf-archive-native"
DEPENDS += "sdbusplus sdbusplus-native"
DEPENDS += "phosphor-dbus-interfaces"
DEPENDS += "libevdev"
DEPENDS += "phosphor-logging"
DEPENDS += "systemd"
DEPENDS += "boost"
DEPENDS += "libgpiod"
DEPENDS += "cli11"
DEPENDS += "nlohmann-json"

CONFIG_JSON_FILE ?= "gpio-default"

SYSTEMD_SERVICE_${PN}-monitor += "phosphor-multi-gpio-monitor@.service"
SYSTEMD_SERVICE_${PN}-monitor += "phosphor-multi-gpio-monitor@${CONFIG_JSON_FILE}.service"
SYSTEMD_SERVICE_${PN}-monitor += "phosphor-gpio-monitor@.service"
SYSTEMD_SERVICE_${PN}-presence += "phosphor-gpio-presence@.service"

FILES_${PN}-monitor += "${bindir}/phosphor-gpio-monitor"
FILES_${PN}-monitor += "${bindir}/phosphor-multi-gpio-monitor"
FILES_${PN}-monitor += "${bindir}/phosphor-gpio-util"
FILES_${PN}-presence += "${bindir}/phosphor-gpio-presence"

SRC_URI += "git://github.com/openbmc/phosphor-gpio-monitor"
SRC_URI += "file://${CONFIG_JSON_FILE}.json"
SRCREV = "86d16f037350afd379d062ca17cab5d553a1c8f2"

do_install_append() {
        install -m 0755 -d ${D}${sysconfdir}
        install -m 0644 ${WORKDIR}/${CONFIG_JSON_FILE}.json ${D}${sysconfdir}/${CONFIG_JSON_FILE}.json
}

S = "${WORKDIR}/git"

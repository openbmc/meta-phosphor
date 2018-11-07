SUMMARY = "Phosphor Host logger"
DESCRIPTION = "Save log messages from host's console to the persistent storage."
HOMEPAGE = "https://github.com/openbmc/phosphor-hostlogger"
PR = "r1"
PV = "1.0+git${SRCPV}"

inherit autotools
inherit pkgconfig
inherit pythonnative
inherit obmc-phosphor-sdbus-service

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=e3fc50a88d0a364313df4b21ef20c29e"

DEPENDS += "sdbusplus \
            sdbusplus-native \
            phosphor-dbus-interfaces"

RDEPENDS_${PN} += "obmc-console"

DBUS_SERVICE_${PN} = "xyz.openbmc_project.HostLogger.service"

OBMC_CONSOLE_HOST_TTY ?= "ttyVUART0"
SYSTEMD_SUBSTITUTIONS += "OBMC_CONSOLE_HOST_TTY:${OBMC_CONSOLE_HOST_TTY}:${DBUS_SERVICE_${PN}}"

S = "${WORKDIR}/git"
SRC_URI += "git://github.com/openbmc/phosphor-hostlogger"
SRCREV = "432bdd655fa78427288cd9fbd5c6acf8c9954bf6"

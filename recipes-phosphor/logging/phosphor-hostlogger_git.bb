SUMMARY = "Phosphor Host logger"
DESCRIPTION = "Save log messages from host's console to the persistent storage."
HOMEPAGE = "https://github.com/openbmc/phosphor-hostlogger"
PR = "r1"
PV = "1.0+git${SRCPV}"

inherit autotools
inherit pkgconfig
inherit pythonnative
inherit obmc-phosphor-license
inherit obmc-phosphor-sdbus-service

DEPENDS += "sdbusplus \
            sdbusplus-native \
            phosphor-dbus-interfaces"

RDEPENDS_${PN} += "sdbusplus \
                   phosphor-dbus-interfaces"

DBUS_SERVICE_${PN} = "xyz.openbmc_project.HostLogger.service"

OBMC_CONSOLE_HOST_TTY ?= "ttyVUART0"
SYSTEMD_SUBSTITUTIONS += "OBMC_CONSOLE_HOST_TTY:${OBMC_CONSOLE_HOST_TTY}:${DBUS_SERVICE_${PN}}"

S = "${WORKDIR}/git"
SRC_URI += "git://github.com/openbmc/phosphor-hostlogger"
SRCREV = "efd5d74432b23dd61aaf4b7acd5cef879b08ffbb"


SUMMARY = "Phosphor Host logger"
DESCRIPTION = "Save log messages from host's console to the persistent storage."
HOMEPAGE = "https://github.com/openbmc/phosphor-hostlogger"
PR = "r1"
PV = "1.0+git${SRCPV}"

inherit autotools
inherit pkgconfig
inherit pythonnative
inherit systemd

# License info
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e3fc50a88d0a364313df4b21ef20c29e"

# Dependencies
DEPENDS += "sdbusplus \
            sdbusplus-native \
            phosphor-dbus-interfaces"
RDEPENDS_${PN} += "bash \
                   obmc-console"
RRECOMMENDS_${PN} += "phosphor-debug-collector"

# systemd service setup
SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "hostlogger.service"
SYSTEMD_DEFAULT_TARGET ?= "multi-user.target"

# Host TTY setup
OBMC_CONSOLE_HOST_TTY ?= "ttyVUART0"

# Extra parameters for 'configure' script
EXTRA_OECONF = "HOST_TTY=${OBMC_CONSOLE_HOST_TTY} \
                SYSTEMD_TARGET=${SYSTEMD_DEFAULT_TARGET}"

# Integration with phosphor-debug-collector (dreport)
FILES_${PN} += "${datadir}/dreport.d/plugins.d/hostlogger \
                ${datadir}/dreport.d/pl_core.d/hostlogger \
                ${datadir}/dreport.d/pl_elog.d/hostlogger \
                ${datadir}/dreport.d/pl_user.d/hostlogger"

# Source code repository
S = "${WORKDIR}/git"
SRC_URI = "git://github.com/openbmc/phosphor-hostlogger"
SRCREV = "1500f4f6796fb86cea30bf1a8bbdeb5233c28e14"

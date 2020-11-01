SUMMARY = "BMC Health Monitoring"
DESCRIPTION = "Daemon to collect and monitor bmc health statistics"
HOMEPAGE = "https://github.com/openbmc/phosphor-health-monitor"
PR = "r1"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=9e69ba356fa59848ffd865152a3ccc13"

inherit meson pkgconfig
inherit systemd
inherit obmc-phosphor-dbus-service

DEPENDS += "sdbusplus"
DEPENDS += "phosphor-dbus-interfaces"
DEPENDS += "sdeventplus"
DEPENDS += "phosphor-logging"
DEPENDS += "nlohmann-json"

SRC_URI = "git://github.com/openbmc/phosphor-health-monitor.git;protocol=git"
SRC_URI += "file://xyz.openbmc_project.HealthMon.conf"
SRCREV = "1d0d012063f962dae3b8ab06f31c7e23559673f8"
S = "${WORKDIR}/git"

_INSTALL_DBUS_CONFIGS = "xyz.openbmc_project.HealthMon.conf"

SYSTEMD_SERVICE_${PN} = "phosphor-health-monitor.service"

SUMMARY = "dbus-sensors"
DESCRIPTION = "Dbus Sensor Services Configured from D-Bus"

SRC_URI = "git://github.com/openbmc/dbus-sensors.git"
SRCREV = "dadbbb5859828ee4072968b9e8d5105e346bd591"

PV = "0.1+git${SRCPV}"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

DBUS_SENSORS_PACKAGES = " \
    ${PN}-adcsensor \
    ${PN}-cpusensor \
    ${PN}-exitairtempsensor \
    ${PN}-fansensor \
    ${PN}-hwmontempsensor \
    ${PN}-intrusionsensor \
    ${PN}-ipmbsensor \
    ${PN}-mcutempsensor \
    ${PN}-psusensor \
    "

PACKAGE_BEFORE_PN += "${DBUS_SENSORS_PACKAGES}"
ALLOW_EMPTY_${PN} = "1"

PACKAGECONFIG ??= " \
    adcsensor \
    cpusensor \
    exitairtempsensor \
    fansensor \
    hwmontempsensor \
    intrusionsensor \
    ipmbsensor \
    mcutempsensor \
    psusensor \
    "

PACKAGECONFIG[adcsensor] = ", -DDISABLE_ADC=ON"
PACKAGECONFIG[cpusensor] = ", -DDISABLE_CPU=ON"
PACKAGECONFIG[exitairtempsensor] = ", -DDISABLE_EXIT_AIR=ON"
PACKAGECONFIG[fansensor] = ", -DDISABLE_FAN=ON"
PACKAGECONFIG[hwmontempsensor] = ", -DDISABLE_HWMON_TEMP=ON"
PACKAGECONFIG[intrusionsensor] = ", -DDISABLE_INTRUSION=ON"
PACKAGECONFIG[ipmbsensor] = ", -DDISABLE_IPMB=ON"
PACKAGECONFIG[mcutempsensor] = ", -DDISABLE_MCUTEMP=ON"
PACKAGECONFIG[psusensor] = ", -DDISABLE_PSU=ON"

SYSTEMD_SERVICE_${PN}-adcsensor = "xyz.openbmc_project.adcsensor.service"
SYSTEMD_SERVICE_${PN}-cpusensor = "xyz.openbmc_project.cpusensor.service"
SYSTEMD_SERVICE_${PN}-exitairtempsensor = "xyz.openbmc_project.exitairsensor.service"
SYSTEMD_SERVICE_${PN}-fansensor = "xyz.openbmc_project.fansensor.service"
SYSTEMD_SERVICE_${PN}-hwmontempsensor = "xyz.openbmc_project.hwmontempsensor.service"
SYSTEMD_SERVICE_${PN}-intrusionsensor = "xyz.openbmc_project.intrusionsensor.service"
SYSTEMD_SERVICE_${PN}-ipmbsensor = "xyz.openbmc_project.ipmbsensor.service"
SYSTEMD_SERVICE_${PN}-mcutempsensor = "xyz.openbmc_project.mcutempsensor.service"
SYSTEMD_SERVICE_${PN}-psusensor = "xyz.openbmc_project.psusensor.service"

FILES_${PN}-adcsensor += "${systemd_system_unitdir}/xyz.openbmc_project.adcsensor.service"
FILES_${PN}-cpusensor += "${systemd_system_unitdir}/xyz.openbmc_project.cpusensor.service"
FILES_${PN}-exitairtempsensor += "${systemd_system_unitdir}/xyz.openbmc_project.exitairsensor.service"
FILES_${PN}-fansensor += "${systemd_system_unitdir}/xyz.openbmc_project.fansensor.service"
FILES_${PN}-hwmontempsensor += "${systemd_system_unitdir}/xyz.openbmc_project.hwmontempsensor.service"
FILES_${PN}-intrusionsensor += "${systemd_system_unitdir}/xyz.openbmc_project.intrusionsensor.service"
FILES_${PN}-ipmbsensor += "${systemd_system_unitdir}/xyz.openbmc_project.ipmbsensor.service"
FILES_${PN}-mcutempsensor += "${systemd_system_unitdir}/xyz.openbmc_project.mcutempsensor.service"
FILES_${PN}-psusensor += "${systemd_system_unitdir}/xyz.openbmc_project.psusensor.service"

DEPENDS = "boost nlohmann-json sdbusplus i2c-tools libgpiod"
inherit cmake systemd

S = "${WORKDIR}/git"

EXTRA_OECMAKE = "-DYOCTO=1"

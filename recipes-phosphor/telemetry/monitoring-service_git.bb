SUMMARY = "MonitoringService"
DESCRIPTION = "Backend service for Redfish Telemetry Service"
HOMEPAGE = "https://github.com/openbmc/telemetry"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=e3fc50a88d0a364313df4b21ef20c29e"

SRC_URI = "git://github.com/openbmc/telemetry.git;protocol=ssh"

PV = "1.0+git${SRCPV}"
SRCREV = "d8726f5b0692ba12948b055a583bc5f8c3f464c7"

S = "${WORKDIR}/git"

inherit cmake
inherit systemd

DEPENDS = "boost \
           systemd \
           sdbusplus"

EXTRA_OECMAKE = "-DYOCTO_DEPENDENCIES=ON -DENABLE_UT=OFF"

SYSTEMD_SERVICE_${PN} += "xyz.openbmc_project.MonitoringService.service"

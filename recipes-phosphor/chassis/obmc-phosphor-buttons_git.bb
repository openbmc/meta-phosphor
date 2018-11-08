SUMMARY = "OpenBMC Buttons"
DESCRIPTION = "OpenBMC All buttons"
PR = "r1"
PV = "1.0+git${SRCPV}"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

S = "${WORKDIR}/git"
SRC_URI += "git://github.com/openbmc/phosphor-buttons.git"
SRCREV = "93894f6eb4703ce5cffbc0cb748499599cdd2e2f"

inherit cmake pkgconfig pythonnative
inherit obmc-phosphor-dbus-service

DBUS_SERVICE_${PN} += "xyz.openbmc_project.Chassis.Buttons@.service"

BUTTONS_TMPL_CTRL = "xyz.openbmc_project.Chassis.Buttons@.service"
#SYSD_TGT = "${SYSTEMD_DEFAULT_TARGET}"
BUTTONS_INSTFMT_CTRL = "xyz.openbmc_project.Chassis.Buttons@{0}.service"
BUTTONS_FMT_CTRL = "../${BUTTONS_TMPL_CTRL}:${SYSD_TGT}.wants/${BUTTONS_INSTFMT_CTRL}"
SYSTEMD_LINK_${PN} += "${@compose_list_zip(d, 'BUTTONS_FMT_CTRL', 'OBMC_HOST_INSTANCES')}"

DEPENDS += " \
    autoconf-archive-native \
    boost \
    systemd \
    sdbusplus \
    sdbusplus-native \
    phosphor-dbus-interfaces \
    phosphor-dbus-interfaces-native \
    phosphor-logging \
    "

EXTRA_OECMAKE = " -DENABLE_GTEST=OFF "


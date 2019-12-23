HOMEPAGE = "https://github.com/openbmc/phosphor-psu-code-mgmt"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"
SRC_URI += "git://github.com/openbmc/phosphor-psu-code-mgmt"
SRCREV = "8afeee56a30ec8097c6ce1b53173f78dc775cc36"
SUMMARY = "Phosphor PSU software manager"
DESCRIPTION = "Providing PSU firmware version and upgrade"

PR = "r1"
PV = "1.0+git${SRCPV}"

inherit meson
inherit pkgconfig
inherit systemd
inherit obmc-phosphor-dbus-service

S = "${WORKDIR}/git"

DEPENDS = " \
         phosphor-logging \
         phosphor-dbus-interfaces \
         sdbusplus \
         openssl \
         "

# The default config of this repo depends on utils from phosphor-power.
# If your system does not depend on phosphor-power, please use
# RDEPENDS_${PN}_remove to remove the dependency.
RDEPENDS_${PN} += "phosphor-power"

# The below configs are expected to be overriden by machine layer

## The psutils here comes from phosphor-power repo where
## * PSU_VERSION_UTIL accepts a PSU inventory path and returns the PSU
##   firmware version string
## * PSU_VERSION_COMPARE_UTIL accepts several PSU inventory paths and return
##   the newest version string
PSU_VERSION_UTIL ?= "-DPSU_VERSION_UTIL='/usr/bin/psutils --raw --get-version'"
PSU_VERSION_COMPARE_UTIL ?= "-DPSU_VERSION_COMPARE_UTIL='/usr/bin/psutils --raw --compare'"

## The psu-update@.service here is an example service that:
## * is started with the PSU inventory path and image directory as the arguments
## * invokes psutils to do the update
PSU_UPDATE_SERVICE ?= "-DPSU_UPDATE_SERVICE=psu-update@.service"

EXTRA_OEMESON = " \
        -Dtests=disabled \
        ${PSU_VERSION_UTIL} \
        ${PSU_VERSION_COMPARE_UTIL} \
        ${PSU_UPDATE_SERVICE} \
        "

DBUS_SERVICE_${PN} += "xyz.openbmc_project.Software.Psu.Updater.service"
SYSTEMD_SERVICE_${PN} += "psu-update@.service"

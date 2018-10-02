SUMMARY = "Entity Manager"
DESCRIPTION = "Entity Manager provides d-bus configuration data \
and configures device tree overlays"

SRC_URI = "git://github.com/openbmc/entity-manager.git"
SRCREV = "b5e32c2396ac531f14d45d29dd4c6e159dcb1e0f"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENCE;md5=a6a4edad4aed50f39a66d098d74b265b"

SYSTEMD_SERVICE_${PN} = "xyz.openbmc_project.EntityManager.service \
                         xyz.openbmc_project.FruDevice.service"

FILES_${PN} += "/usr/share/overlay_templates/* \
                /usr/share/configurations/* \
                /usr/share/overlay_templates/*"

PROVIDES += "virtual/phosphor-fans-sensor-inventory"
DEPENDS = "boost \
           i2c-tools \
           nlohmann-json \
           sdbusplus \
           valijson"

S = "${WORKDIR}/git/"
inherit cmake obmc-phosphor-systemd

EXTRA_OECMAKE = "-DYOCTO=1 -DUSE_OVERLAYS=0"


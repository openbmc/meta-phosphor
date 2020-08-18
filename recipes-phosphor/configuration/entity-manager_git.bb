SUMMARY = "Entity Manager"
DESCRIPTION = "Entity Manager provides d-bus configuration data \
and configures system sensors"

SRC_URI = "git://github.com/openbmc/entity-manager.git"
SRCREV = "a9804f465099882fdef0c5548dcc6fde50d04560"
PV = "0.1+git${SRCPV}"

SRC_URI_append_ibm-power9-cpu = " file://Avoid-Power9-CFAM-I2C-buses.patch"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENCE;md5=a6a4edad4aed50f39a66d098d74b265b"

SYSTEMD_SERVICE_${PN} = "xyz.openbmc_project.EntityManager.service \
                         ${@bb.utils.contains('DISTRO_FEATURES', 'ipmi-fru', 'xyz.openbmc_project.FruDevice.service', '', d)}"
SYSTEMD_AUTO_ENABLE_${PN}_ibm-power-cpu = "disable"

DEPENDS = "boost \
           nlohmann-json \
           sdbusplus \
           valijson"

S = "${WORKDIR}/git/"
inherit meson systemd

EXTRA_OEMESON = "-Dtests=disabled"

PACKAGECONFIG ??= "${@bb.utils.filter('DISTRO_FEATURES', 'ipmi-fru', d)}"
PACKAGECONFIG[ipmi-fru] = "-Dfru-device=true, -Dfru-device=false, i2c-tools,"

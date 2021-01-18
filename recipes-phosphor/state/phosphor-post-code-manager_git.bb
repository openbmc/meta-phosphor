SUMMARY = "Phosphor post code manager"
DESCRIPTION = "Phosphor post Code Manager monitors post code posted on dbus \
interface /xyz/openbmc_project/state/boot/raw by snoopd daemon and save them \
in a file under /var/lib for history."

SRC_URI = "git://github.com/openbmc/phosphor-post-code-manager.git"
SRC_URI += "file://${PN}.conf"
SRCREV = "fd45f78858304f67a0e80224a99cbe76eed82af0"

S = "${WORKDIR}/git"

PV = "1.0+git${SRCPV}"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

inherit cmake pkgconfig systemd obmc-phosphor-dbus-service


def get_service(d):
    if(d.getVar('OBMC_HOST_INSTANCES') == '0'):
      return "xyz.openbmc_project.State.Boot.PostCode.service"
    else:
      return " ".join(["xyz.openbmc_project.State.Boot.PostCode@{}.service".format(x) for x in d.getVar('OBMC_HOST_INSTANCES').split()])

DBUS_PACKAGES = "${PN}"

SYSTEMD_SERVICE_${PN} = "${@get_service(d)}"

_INSTALL_DBUS_CONFIGS = "${PN}.conf"

DEPENDS += " \
    sdbusplus \
    phosphor-dbus-interfaces \
    phosphor-logging \
    "

FILES_${PN}  += "${systemd_system_unitdir}/xyz.openbmc_project.State.Boot.PostCode@.service"
FILES_${PN}  += "${systemd_system_unitdir}/xyz.openbmc_project.State.Boot.PostCode.service"

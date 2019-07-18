HOMEPAGE = "http://github.com/openbmc/phosphor-ipmi-flash"
SUMMARY = "Phosphor OEM IPMI In-band Firmware Update over BLOB"
DESCRIPTION = "This package handles a series of OEM IPMI commands that implement the firmware update handler over the BLOB protocol."
PR = "r1"
PV = "1.0+git${SRCPV}"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

inherit autotools pkgconfig
inherit obmc-phosphor-ipmiprovider-symlink
inherit systemd

DEPENDS += "autoconf-archive-native"
DEPENDS += "phosphor-ipmi-blobs"
DEPENDS += "phosphor-logging"
DEPENDS += "sdbusplus"
DEPENDS += "systemd"
DEPENDS += "ipmi-blob-tool"
DEPENDS += "pciutils"

PACKAGECONFIG ?= "cleanup-delete"
PACKAGECONFIG[cleanup-delete] = "--enable-cleanup-delete, --disable-cleanup-delete"
# If using static-layout, reboot-update is a good option to handle updating.
PACKAGECONFIG[reboot-update] = "--enable-reboot-update, --disable-reboot-update"
PACKAGECONFIG[host-bios] = "--enable-host-bios, --disable-host-bios"

EXTRA_OECONF = "--disable-tests --disable-build-host-tool"

S = "${WORKDIR}/git"
SRC_URI = "git://github.com/openbmc/phosphor-ipmi-flash"
SRCREV = "298930a18d01b4685aab3a7018299c7342afa028"

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} += "phosphor-ipmi-flash-bmc-prepare.target \
 phosphor-ipmi-flash-bmc-verify.target \
 phosphor-ipmi-flash-bmc-update.target"

# If they enabled host-bios, add those three extra targets.
HOST_BIOS_TARGETS = "phosphor-ipmi-flash-bios-prepare.target \
 phosphor-ipmi-flash-bios-verify.target \
 phosphor-ipmi-flash-bios-update.target"

SYSTEMD_SERVICE_${PN} += "${@bb.utils.contains('PACKAGECONFIG', 'host-bios', '${HOST_BIOS_TARGETS}', '', d)}"

FILES_${PN}_append = " ${libdir}/ipmid-providers/lib*${SOLIBS}"
FILES_${PN}_append = " ${libdir}/blob-ipmid/lib*${SOLIBS}"
FILES_${PN}-dev_append = " ${libdir}/ipmid-providers/lib*${SOLIBSDEV} ${libdir}/ipmid-providers/*.la"

BLOBIPMI_PROVIDER_LIBRARY += "libfirmwareblob.so"
BLOBIPMI_PROVIDER_LIBRARY += "${@bb.utils.contains('PACKAGECONFIG', 'cleanup-delete', 'libfirmwarecleanupblob.so', '', d)}"

do_configure[depends] += "virtual/kernel:do_shared_workdir"

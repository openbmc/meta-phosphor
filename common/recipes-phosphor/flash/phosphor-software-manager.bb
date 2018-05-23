SUMMARY = "Phosphor Software Management"
DESCRIPTION = "Phosphor Software Manager provides a set of system software \
management daemons. It is suitable for use on a wide variety of OpenBMC \
platforms."
HOMEPAGE = "https://github.com/openbmc/phosphor-bmc-code-mgmt"
PR = "r1"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=e3fc50a88d0a364313df4b21ef20c29e"

SOFTWARE_MGR_PACKAGES = " \
    ${PN}-version \
    ${PN}-download-mgr \
    ${PN}-updater \
    ${PN}-updater-ubi \
    ${PN}-sync \
"
PACKAGE_BEFORE_PN += "${SOFTWARE_MGR_PACKAGES}"
ALLOW_EMPTY_${PN} = "1"

DBUS_PACKAGES = "${SOFTWARE_MGR_PACKAGES}"

# Set SYSTEMD_PACKAGES to empty because we do not want ${PN} and DBUS_PACKAGES
# handles the rest.
SYSTEMD_PACKAGES = ""

PACKAGECONFIG[verify_signature] = "--enable-verify_signature,--disable-verify_signature"
PACKAGECONFIG[sync_bmc_files] = "--enable-sync_bmc_files,--disable-sync_bmc_files"
PACKAGECONFIG[ubifs_layout] = "--enable-ubifs_layout"

inherit autotools pkgconfig
inherit obmc-phosphor-dbus-service
inherit pythonnative
inherit ${@bb.utils.contains('DISTRO_FEATURES', 'obmc-ubi-fs', 'phosphor-software-manager-ubi-fs', '', d)}

DEPENDS += " \
    autoconf-archive-native \
    sdbusplus \
    phosphor-dbus-interfaces \
    phosphor-logging \
    sdbus++-native \
"

RDEPENDS_${PN}-version += " \
    phosphor-logging \
    phosphor-dbus-interfaces \
    sdbusplus \
"
RDEPENDS_${PN}-download-mgr += " \
    phosphor-logging \
    phosphor-dbus-interfaces \
    sdbusplus \
"
RDEPENDS_${PN}-updater += " \
    phosphor-logging \
    phosphor-dbus-interfaces \
    sdbusplus \
    virtual-obmc-image-manager \
"

RPROVIDES_${PN}-version += " \
    virtual-obmc-image-manager \
"

FILES_${PN}-version += "${sbindir}/phosphor-version-software-manager ${exec_prefix}/lib/tmpfiles.d/software.conf"
FILES_${PN}-download-mgr += "${sbindir}/phosphor-download-manager"
FILES_${PN}-updater += "${sbindir}/phosphor-image-updater"
FILES_${PN}-sync += " \
    ${sbindir}/phosphor-sync-software-manager \
    ${sysconfdir}/synclist \
    "
DBUS_SERVICE_${PN}-version += "xyz.openbmc_project.Software.Version.service"
DBUS_SERVICE_${PN}-download-mgr += "xyz.openbmc_project.Software.Download.service"
DBUS_SERVICE_${PN}-updater += "xyz.openbmc_project.Software.BMC.Updater.service"
DBUS_SERVICE_${PN}-sync += "xyz.openbmc_project.Software.Sync.service"

SRC_URI += "file://software.conf"

do_install_append() {
    # /tmp/images is the software image upload directory.
    # It should not be deleted since it is watched by the Image Manager
    # for new images.

    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true', 'false', d)}; then
        install -d ${D}${exec_prefix}/lib/tmpfiles.d
        install -m 644 ${WORKDIR}/software.conf ${D}${exec_prefix}/lib/tmpfiles.d/
    fi
}

SRC_URI += "git://github.com/openbmc/phosphor-bmc-code-mgmt"
SRCREV = "aea48f27e46be9454e79a77e3594e021464ec903"

S = "${WORKDIR}/git"

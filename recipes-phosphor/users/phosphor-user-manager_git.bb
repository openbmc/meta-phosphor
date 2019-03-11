SUMMARY = "Phosphor User Manager Daemon"
DESCRIPTION = "Daemon that does user management"
HOMEPAGE = "http://github.com/openbmc/phosphor-user-manager"
PR = "r1"
PV = "1.0+git${SRCPV}"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e3fc50a88d0a364313df4b21ef20c29e"

inherit autotools pkgconfig
inherit obmc-phosphor-dbus-service

DEPENDS += "autoconf-archive-native"
DEPENDS += "sdbusplus"
DEPENDS += "phosphor-logging"
DEPENDS += "phosphor-dbus-interfaces"
DEPENDS += "boost"
DEPENDS += "nss-pam-ldapd"
PACKAGE_BEFORE_PN = "phosphor-ldap"
RDEPENDS_${PN} += "libsystemd"
RDEPENDS_${PN} += "phosphor-logging"

inherit useradd

USERADD_PACKAGES = "${PN} phosphor-ldap"
DBUS_PACKAGES = "${USERADD_PACKAGES}"
# add groups needed for privilege maintenance
GROUPADD_PARAM_${PN} = "priv-admin; priv-operator; priv-user; priv-callback "
GROUPADD_PARAM_phosphor-ldap = "priv-admin; priv-operator; priv-user; priv-callback "

# Add root user to priv-admin group
GROUPMEMS_PARAM_${PN} = "-g priv-admin -a root"

DBUS_SERVICE_${PN} += "xyz.openbmc_project.User.Manager.service"
FILES_phosphor-ldap += " \
        ${sbindir}/phosphor-ldap-conf \
        ${sbindir}/phosphor-ldap-mapper \
"
DBUS_SERVICE_phosphor-ldap = " \
        xyz.openbmc_project.Ldap.Config.service \
        xyz.openbmc_project.LDAP.PrivilegeMapper.service \
"
SRC_URI += "git://github.com/openbmc/phosphor-user-manager"
SRCREV = "95a2931473dfa61a30e7a65606dab15ab24cd5b4"
S = "${WORKDIR}/git"

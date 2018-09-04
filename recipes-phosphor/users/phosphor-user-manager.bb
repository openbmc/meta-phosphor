SUMMARY = "Phosphor User Manager Daemon"
DESCRIPTION = "Daemon that does user management"
HOMEPAGE = "http://github.com/openbmc/phosphor-user-manager"
PR = "r1"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e3fc50a88d0a364313df4b21ef20c29e"
inherit autotools pkgconfig
inherit obmc-phosphor-dbus-service

DEPENDS += "autoconf-archive-native"
DEPENDS += "sdbusplus"
DEPENDS += "phosphor-logging"
DEPENDS += "phosphor-dbus-interfaces"
DEPENDS += "boost"

inherit useradd

USERADD_PACKAGES = " \
        phosphor-user-mgr \
        phosphor-ldap \
"

ALLOW_EMPTY_${PN} = "1"
PACKAGE_BEFORE_PN += "${USERADD_PACKAGES}"
SYSTEMD_PACKAGES = "${USERADD_PACKAGES}"
DBUS_PACKAGES = "${USERADD_PACKAGES}"

RDEPENDS_phosphor-user-mgr += "libsystemd phosphor-logging"
DBUS_SERVICE_phosphor-user-mgr += "xyz.openbmc_project.User.Manager.service"
# add groups needed for privilege maintenance
GROUPADD_PARAM_phosphor-user-mgr = "priv-admin; priv-operator; priv-user; priv-callback "
FILES_phosphor-user-mgr += " \
        ${sbindir}/phosphor-user-manager \
"

RDEPENDS_phosphor-ldap += "libsystemd phosphor-logging"
DBUS_SERVICE_phosphor-ldap += "xyz.openbmc_project.Ldap.Config.service"
GROUPADD_PARAM_phosphor-ldap = "priv-admin; priv-operator; priv-user; priv-callback "
FILES_phosphor-ldap += " \
        ${sbindir}/phosphor-ldap-conf \
"

SRC_URI += "git://github.com/openbmc/phosphor-user-manager"
SRCREV = "9f630d9eb0ce1c103532a4304ea080066199094e"
S = "${WORKDIR}/git"

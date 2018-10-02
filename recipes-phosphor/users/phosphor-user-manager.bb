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
DEPENDS += "nss-pam-ldapd"
PACKAGE_BEFORE_PN = "phosphor-ldap"
RDEPENDS_${PN} += "libsystemd"
RDEPENDS_${PN} += "phosphor-logging"

inherit useradd

USERADD_PACKAGES = "${PN}"
# add groups needed for privilege maintenance
GROUPADD_PARAM_${PN} = "priv-admin; priv-operator; priv-user; priv-callback "

DBUS_SERVICE_${PN} += "xyz.openbmc_project.User.Manager.service"
DBUS_SERVICE_phosphor-ldap = "xyz.openbmc_project.Ldap.Config.service"
FILES_phosphor-ldap = " \
        ${sbindir}/phosphor-ldap-conf \
"
SRC_URI += "git://github.com/openbmc/phosphor-user-manager"
SRCREV = "9f630d9eb0ce1c103532a4304ea080066199094e"
S = "${WORKDIR}/git"

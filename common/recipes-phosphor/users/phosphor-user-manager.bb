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
	${PN} \
	phosphor-ldap-config \
"

RDEPENDS_${PN} += "libsystemd phosphor-logging"
DBUS_SERVICE_${PN} += "xyz.openbmc_project.User.Manager.service"
# add groups needed for privilege maintenance
GROUPADD_PARAM_${PN} = "priv-admin; priv-operator; priv-user; priv-callback "


GROUPADD_PARAM_phosphor-ldap-config = "priv-admin; priv-operator; priv-user; priv-callback "

RDEPENDS_phosphor-ldap-config += "libsystemd phosphor-logging"
DBUS_SERVICE_phosphor-ldap-config += "xyz.openbmc_project.Ldap.Config.service"
FILES_phosphor-ldap-config += " \
        ${sbindir}/phosphor-ldap-conf \
"

SRC_URI += "git://github.com/openbmc/phosphor-user-manager"
SRCREV = "10eb23f8d64d197dade920178b193c1979235156"
S = "${WORKDIR}/git"

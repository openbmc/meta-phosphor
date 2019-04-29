inherit systemd
inherit useradd

USERADD_PACKAGES = "${PN}"

# add a user called httpd for the server to assume
USERADD_PARAM_${PN} = "-r -s /usr/sbin/nologin bmcweb"
GROUPADD_PARAM_${PN} = "web; redfish"
# Add root user to web & redfish group
GROUPMEMS_PARAM_${PN} = "-g web -a root; -g redfish -a root"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENCE;md5=a6a4edad4aed50f39a66d098d74b265b"

SRC_URI = "git://github.com/openbmc/bmcweb.git"

PV = "1.0+git${SRCPV}"
SRCREV = "0e7de46f9b6365bad4e79a3933112750c5bf7853"

S = "${WORKDIR}/git"

DEPENDS = "openssl zlib boost libpam sdbusplus gtest nlohmann-json libtinyxml2 "

RDEPENDS_${PN} += "jsnbd"

FILES_${PN} += "${datadir}/** "

inherit cmake

https_certificate_dir="${sysconfdir}/ssl/certs/https/"
EXTRA_OECMAKE = "-DBMCWEB_BUILD_UT=OFF -DYOCTO_DEPENDENCIES=ON -DCERTIFICATE_PATH=${https_certificate_dir}"

SYSTEMD_SERVICE_${PN} += "bmcweb.service bmcweb.socket"

FULL_OPTIMIZATION = "-Os -pipe "

do_install_append() {
    rm -rf ${D}${includedir}/dbus
    rm -rf ${D}${libdir}/cmake
}

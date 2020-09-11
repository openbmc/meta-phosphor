inherit systemd
inherit useradd

USERADD_PACKAGES = "${PN}"

# add a user called httpd for the server to assume
USERADD_PARAM_${PN} = "-r -s /usr/sbin/nologin bmcweb"
GROUPADD_PARAM_${PN} = "web; redfish"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENCE;md5=a6a4edad4aed50f39a66d098d74b265b"

SRC_URI = "git://github.com/openbmc/bmcweb.git"

PV = "1.0+git${SRCPV}"
SRCREV = "7c486a180c492d5f0080ad59ad44b0b59787ac31"

S = "${WORKDIR}/git"


DEPENDS = "openssl \
           zlib \
           boost \
           boost-url \
           libpam \
           sdbusplus \
           gtest \
           nlohmann-json \
           libtinyxml2 "

RDEPENDS_${PN} += "jsnbd"

FILES_${PN} += "${datadir}/** "

inherit cmake

PACKAGECONFIG ??= "enable-https-redirect"
PACKAGECONFIG[enable-https-redirect] = " \
-DBMCWEB_ENABLE_HTTPS_REDIRECT=ON,-DBMCWEB_ENABLE_HTTPS_REDIRECT=OFF \
"

EXTRA_OECMAKE = "-DBMCWEB_BUILD_UT=OFF -DYOCTO_DEPENDENCIES=ON"

SYSTEMD_SERVICE_${PN} = " \
     bmcweb.service \
     bmcweb.socket \
     ${@bb.utils.contains('PACKAGECONFIG', 'enable-https-redirect', 'bmcweb_redirect.socket', '', d)} \
"

FULL_OPTIMIZATION = "-Os -pipe "

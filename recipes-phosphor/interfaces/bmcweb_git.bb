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
SRCREV = "1536473f93d6985d8021e154316f52bb1de0d3c2"

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

inherit meson

EXTRA_OEMESON = "--buildtype=minsize -Db_lto=true -Dtests=disabled -Dyocto-deps=enabled"

SYSTEMD_SERVICE_${PN} += "bmcweb.service bmcweb.socket"

FULL_OPTIMIZATION = "-Os "

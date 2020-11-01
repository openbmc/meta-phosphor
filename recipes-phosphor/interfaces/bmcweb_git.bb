inherit systemd
inherit useradd

USERADD_PACKAGES = "${PN}"

# add a user called httpd for the server to assume
USERADD_PARAM_${PN} = "-r -s /usr/sbin/nologin -m bmcweb"
GROUPADD_PARAM_${PN} = "web; redfish"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENCE;md5=a6a4edad4aed50f39a66d098d74b265b"

SRC_URI = "git://github.com/openbmc/bmcweb.git"

PV = "1.0+git${SRCPV}"
SRCREV = "313efb1c9afcb0192585579a663467c28f873286"

S = "${WORKDIR}/git"

DEPENDS = "openssl \
           zlib \
           boost \
           boost-url \
           libpam \
           sdbusplus \
           gtest \
           nlohmann-json \
           libtinyxml2 \
           phosphor-ipmi-host "

RDEPENDS_${PN} += "jsnbd"

FILES_${PN} += "${datadir}/** "

inherit meson

EXTRA_OEMESON = "--buildtype=debug -Dtests=disabled -Dyocto-deps=enabled -Dbmcweb-logging=enabled"

SYSTEMD_SERVICE_${PN} += "bmcweb.service bmcweb.socket"

FULL_OPTIMIZATION = "-Os "

pkg_postinst_bmcweb_append() {
  if [ -n "$D" ]; then
    chgrp bmcweb $D/etc/ssl/certs
    chmod 0775 $D/etc/ssl/certs
    chmod -R 0770 $D/home/bmcweb
  fi
}

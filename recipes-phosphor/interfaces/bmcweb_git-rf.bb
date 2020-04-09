inherit systemd
inherit useradd

USERADD_PACKAGES = "${PN}"

# add a user called httpd for the server to assume
USERADD_PARAM_${PN} = "-r -s /usr/sbin/nologin bmcweb"
GROUPADD_PARAM_${PN} = "web; redfish"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENCE;md5=a6a4edad4aed50f39a66d098d74b265b"

SRC_URI = "git:///gsa/ausgsa/projects/i/indiateam01/rbailapu/VMICertChanges/bmcweb;branch=vmi;protocol=file"

PV = "1.0+git${SRCPV}"
SRCREV = "f4bb6c8bc21a2d6a8d9aa463ab1466b3a8bd97ab"

S = "${WORKDIR}/git"

DEPENDS = "openssl zlib boost libpam sdbusplus gtest nlohmann-json libtinyxml2 "

RDEPENDS_${PN} += "jsnbd" 

PACKAGECONFIG ??= ""
PACKAGECONFIG[ibm-mc-console] = "-DIBM_MC_CONSOLE=yes, -DIBM_MC_CONSOLE=no, pldm, "

FILES_${PN} += "${datadir}/** "

inherit cmake

EXTRA_OECMAKE = "-DBMCWEB_BUILD_UT=OFF -DYOCTO_DEPENDENCIES=ON -DBMCWEB_ENABLE_IBM_MANAGEMENT_CONSOLE=ON"

SYSTEMD_SERVICE_${PN} += "bmcweb.service bmcweb.socket"

FULL_OPTIMIZATION = "-Os -pipe "

DESCRIPTION = "Header-only C++ library for JSON Schema validation"
HOMEPAGE = "https://github.com/tristanpenman/valijson"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=015106c62262b2383f6c72063f0998f2"

SRC_URI = "git://github.com/tristanpenman/valijson.git"
PV = "0.1+git${SRCPV}"

SRCREV = "c2f22fddf599d04dc33fcd7ed257c698a05345d9"

S = "${WORKDIR}/git"
DEPENDS = "nlohmann-json"
inherit cmake

EXTRA_OECMAKE = "-DINSTALL_HEADERS=1 -DBUILD_TESTS=0"

# Valijson expects json.hpp where nlohmann-json_git.bb installs
# nlohmann/json.hpp, this allows usage via json.hpp
do_install_append() {
    ln -rs ${D}${includedir}/nlohmann/json.hpp ${D}${includedir}/json.hpp
}

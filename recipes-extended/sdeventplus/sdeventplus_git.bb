SUMMARY = "C++ bindings for systemd event APIs"
DESCRIPTION = "C++ bindings for systemd event APIs."
HOMEPAGE = "http://github.com/openbmc/sdeventplus"
PR = "r1"
PV = "0.1+git${SRCPV}"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e3fc50a88d0a364313df4b21ef20c29e"

inherit meson pkgconfig

DEPENDS += " \
        function2 \
        stdplus \
        systemd \
        "

EXTRA_OEMESON = " \
        -Dexamples=false \
        -Dtests=disabled \
        "

SRC_URI += "git://github.com/openbmc/sdeventplus"
SRCREV = "546fa9337b8788c03d4f4892acc33c8601ce92f5"

S = "${WORKDIR}/git"

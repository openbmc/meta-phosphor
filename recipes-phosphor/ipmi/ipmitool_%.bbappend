FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

DEPENDS += "systemd"

SRC_URI = "git://github.com/ipmitool/ipmitool.git;protocol=https"
SRCREV = "c3939dac2c060651361fc71516806f9ab8c38901"

# this extra line causes yocto to download the enterprise-numbers
# instead of having the makefile download it during do_compile
SRC_URI += " \
    https://www.iana.org/assignments/enterprise-numbers \
    "

# these are needed for the enterprise numbers fetch
SRC_URI[md5sum] = "528573751dc60691282ee299c233fcbf"
SRC_URI[sha256sum] = "d96a2efedc27f6b11001da3c26a610bed1829a3e18343149feae5c07737cbe18"

# make sure that the enterprise-numbers file gets installed in the root FS
FILES_${PN} += "/usr/share/misc/enterprise-numbers"

S = "${WORKDIR}/git"
LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=9aa91e13d644326bf281924212862184"

EXTRA_OECONF_append = " --disable-ipmishell --enable-intf-dbus DEFAULT_INTF=dbus "

PV = "1.8.18+git${SRCPV}"

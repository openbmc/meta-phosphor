FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

DEPENDS += "systemd"

SRC_URI = "git://github.com/ipmitool/ipmitool.git;protocol=https"

# TODO: when this is updated, pull a fresh copy of
#       enterprise-numbers to keep it up to date:
#       https://www.iana.org/assignments/enterprise-numbers
SRCREV = "c3939dac2c060651361fc71516806f9ab8c38901"

# this file is manually downloaded so it can be versioned
# instead of having the makefile download it during do_compile
SRC_URI += " \
    file://enterprise-numbers \
    "

# make sure that the enterprise-numbers file gets installed in the root FS
FILES_${PN} += "/usr/share/misc/enterprise-numbers"

S = "${WORKDIR}/git"
LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=9aa91e13d644326bf281924212862184"

EXTRA_OECONF_append = " --disable-ipmishell --enable-intf-dbus DEFAULT_INTF=dbus "

PV = "1.8.18+git${SRCPV}"

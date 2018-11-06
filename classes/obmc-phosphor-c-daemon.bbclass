# Common code for dbus applications using c.
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${PHOSPHORBASE}/COPYING.apache-2.0;md5=34400b68072d710fecd0a2940a0d1658"

inherit obmc-phosphor-systemd

DEPENDS += "glib-2.0"

INSTALL_NAME ?= "${PN}"
BIN_NAME ?= "${INSTALL_NAME}"

do_install_append() {
        # install the binary
        install -d ${D}${sbindir}
        install -m 0755 ${S}/${BIN_NAME} ${D}${sbindir}/${INSTALL_NAME}
}

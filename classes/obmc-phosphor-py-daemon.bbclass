# Common code for dbus applications using python.

inherit allarch
inherit obmc-phosphor-systemd

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${PHOSPHORBASE}/COPYING.apache-2.0;md5=34400b68072d710fecd0a2940a0d1658"

RDEPENDS_${PN} += "python-dbus python-pygobject"
INSTALL_NAME ?= "${PN}"
SCRIPT_NAME ?= "${INSTALL_NAME}.py"

do_install_append() {
        # install the script
        install -d ${D}${sbindir}
        install -m 0755 ${S}/${SCRIPT_NAME} ${D}${sbindir}/${INSTALL_NAME}
}

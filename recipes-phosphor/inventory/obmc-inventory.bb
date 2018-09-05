SUMMARY = "Phosphor Inventory Generation"
DESCRIPTION = "Generates inventory data from the machine readable workbook"
PR = "r1"

inherit mrw-xml

S = "${WORKDIR}/git"

PROVIDES += "virtual/obmc-inventory-data"
RPROVIDES_${PN} += "virtual-obmc-inventory-data"

inherit allarch
inherit obmc-phosphor-license
inherit mrw-rev

DEPENDS += "mrw-native mrw-api-native"

SRC_URI += "${MRW_TOOLS_SRC_URI}"
SRCREV = "${MRW_TOOLS_SRCREV}"

FILES_${PN} += "${datadir}/inventory"

do_compile() {
    ${STAGING_BINDIR_NATIVE}/perl-native/perl ${S}/inventory.pl \
        -x ${STAGING_DIR_NATIVE}${mrw_datadir}/${MRW_XML} -o inventory.json
}

do_install() {
    install -d ${D}${datadir}/inventory
    install -m 0644 inventory.json ${D}${datadir}/inventory/inventory.json
}

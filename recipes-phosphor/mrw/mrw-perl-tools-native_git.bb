SUMMARY = "OpenBMC MRW Perl Tools"
DESCRIPTION = "OpenBMC Perl tools for the machine readable workbook"
S = "${WORKDIR}/git"
PV = "1.0+git${SRCPV}"

inherit obmc-phosphor-license
inherit native
inherit cpan_build
inherit mrw-rev

DEPENDS += "libmodule-build-perl-native mrw-api-native yaml-tiny-native"

SRC_URI += "${MRW_TOOLS_SRC_URI}"
SRCREV = "${MRW_TOOLS_SRCREV}"

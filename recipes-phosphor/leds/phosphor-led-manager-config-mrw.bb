SUMMARY = "Phosphor LED Group Management with MRW generated data"
PR = "r1"
LICENSE = "Apache-2.0"

inherit allarch
inherit mrw-xml

PROVIDES += "virtual/phosphor-led-manager-config${PHOSPHOR_LED_CONFIG_RECIPE_SUFFIX}"
DEPENDS += "mrw-native mrw-perl-tools-native"

# Generate a YAML files based on MRW input
do_install() {
    USE_MRW="${@bb.utils.contains('DISTRO_FEATURES', 'obmc-mrw', 'yes', 'no', d)}"
    DEST=${D}${datadir}/phosphor-led-manager

    if [ "${USE_MRW}" = "yes" ]; then
        install -d ${DEST}/
        ${STAGING_BINDIR_NATIVE}/perl-native/perl \
        ${STAGING_BINDIR_NATIVE}/gen_led_groups.pl \
        -i ${STAGING_DIR_NATIVE}${mrw_datadir}/${MRW_XML} \
        -o ${DEST}/led.yaml
    fi
}

FILES_${PN} += "${datadir}/phosphor-led-manager/led.yaml"

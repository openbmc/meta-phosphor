SUMMARY = "Phosphor LED Group Management with example data"
PR = "r1"
PV = "1.0+git${SRCPV}"

inherit allarch
require phosphor-led-manager.inc

PROVIDES += "virtual/phosphor-led-manager-config${PHOSPHOR_LED_CONFIG_RECIPE_SUFFIX}"
S = "${WORKDIR}/git"

do_install() {
    install -D led.yaml ${D}${datadir}/phosphor-led-manager/led.yaml
}

FILES_${PN} += "${datadir}/phosphor-led-manager/led.yaml"

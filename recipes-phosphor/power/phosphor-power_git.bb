SUMMARY = "Phosphor Power services and utilities"
DESCRIPTION = "Configure and monitor power supplies, power sequencers, and \
voltage regulators, and analyzes power devices for faults"
PR = "r1"
PV = "1.0+git${SRCPV}"

inherit meson
inherit pkgconfig
inherit systemd
inherit python3native

require ${PN}.inc

S = "${WORKDIR}/git"

SRC_URI += " file://0001-Use-python3.patch"

# TODO: in future when openpower-dbus-interfaces is removed from
# phosphor-power, remove the dependency here.
DEPENDS += " \
         phosphor-logging \
         openpower-dbus-interfaces \
         sdbus++-native \
         sdeventplus \
         nlohmann-json \
         cli11 \
         i2c-tools \
         ${PYTHON_PN}-native \
         ${PYTHON_PN}-pyyaml-native \
         ${PYTHON_PN}-setuptools-native \
         ${PYTHON_PN}-mako-native \
         "

PACKAGE_BEFORE_PN = "phosphor-psu-monitor"
FILES_phosphor-psu-monitor = "${bindir}/phosphor-psu-monitor \
                              ${systemd_system_unitdir}/phosphor-psu-monitor.service"

SEQ_MONITOR_SVC = "pseq-monitor.service"
SEQ_PGOOD_SVC = "pseq-monitor-pgood.service"
PSU_MONITOR_TMPL = "power-supply-monitor@.service"
SYSTEMD_SERVICE_${PN} += "${SEQ_MONITOR_SVC} ${SEQ_PGOOD_SVC} ${PSU_MONITOR_TMPL}"

# Package all phosphor-regulators config files
FILES_${PN} += "${datadir}/phosphor-regulators"

# ${PN}-regulators service/systemd setup
REGS_SVC = "phosphor-regulators.service"
SYSTEMD_SERVICE_${PN} += "${REGS_SVC}"

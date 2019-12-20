SUMMARY = "Phosphor Power services and utilities"
DESCRIPTION = "Configure and monitor power supplies, power sequencers, and \
voltage regulators, and analyzes power devices for faults"
PR = "r1"
PV = "1.0+git${SRCPV}"

inherit meson
inherit pkgconfig
inherit systemd
inherit pythonnative

require ${PN}.inc

S = "${WORKDIR}/git"

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
         "

SEQ_MONITOR_SVC = "pseq-monitor.service"
SEQ_PGOOD_SVC = "pseq-monitor-pgood.service"
PSU_MONITOR_TMPL = "power-supply-monitor@.service"
SYSTEMD_SERVICE_${PN} += "${SEQ_MONITOR_SVC} ${SEQ_PGOOD_SVC} ${PSU_MONITOR_TMPL}"

# Package all phosphor-regulators config files
FILES_${PN} += "${datadir}/phosphor-regulators"

# ${PN}-regulators service/systemd setup
REGS_SVC = "phosphor-regulators.service"
SYSTEMD_SERVICE_${PN} += "${REGS_SVC}"

# ${PN}-regulators-configure service/systemd setup
REGS_CONF_SVC = "phosphor-regulators-config.service"
SYSTEMD_SERVICE_${PN} += "${REGS_CONF_SVC}"

# ${PN}-regulators-monitor-enable service/systemd setup
REGS_MON_ENA_SVC = "phosphor-regulators-monitor-enable.service"
SYSTEMD_SERVICE_${PN} += "${REGS_MON_ENA_SVC}"

# ${PN}-regulators-monitor-disable service/systemd setup
REGS_MON_DIS_SVC = "phosphor-regulators-monitor-disable.service"
SYSTEMD_SERVICE_${PN} += "${REGS_MON_DIS_SVC}"

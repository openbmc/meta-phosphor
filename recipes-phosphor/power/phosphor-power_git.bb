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

# ${PN}-regulators service/systemd setup
TMPL_REGS = "phosphor-regulators@.service"
SYSTEMD_SERVICE_${PN} += "${TMPL_REGS}"

# ${PN}-regulators-configure service/systemd setup
TMPL_REGS_CONF = "phosphor-regulators-config@.service"
SYSTEMD_SERVICE_${PN} += "${TMPL_REGS_CONF}"

# ${PN}-regulators-monitor service/systemd setup
TMPL_REGS_MON = "phosphor-regulators-monitor@.service"
SYSTEMD_SERVICE_${PN} += "${TMPL_REGS_MON}"

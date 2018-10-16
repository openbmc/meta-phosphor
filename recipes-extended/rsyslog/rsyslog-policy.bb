SUMMARY = "Policy configuration for rsyslog"
PR = "r1"

inherit obmc-phosphor-license

DEPENDS += "rsyslog"

FILES_${PN} += "${systemd_system_unitdir}/rsyslog.service.d/rsyslog-override.conf"

SRC_URI += "file://rsyslog-override.conf"

do_install() {
    install -d ${D}${systemd_system_unitdir}/rsyslog.service.d
    install -m 0644 ${WORKDIR}/rsyslog-override.conf \
        ${D}${systemd_system_unitdir}/rsyslog.service.d/rsyslog-override.conf
}

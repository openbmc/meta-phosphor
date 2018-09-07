FILESEXTRAPATHS_prepend_df-obmc-ubi-fs := "${THISDIR}/${PN}:"

RDEPENDS_${PN}_append_df-obmc-ubi-fs = " preinit-mounts"

do_install_append() {
    install -d ${D}/srv

    if [ "${@bb.utils.filter('DISTRO_FEATURES', 'ldap', d)}" ]; then
        install -D -m 644 ${WORKDIR}/nsswitch_ldap.conf ${D}/${sysconfdir}/nsswitch.conf
    fi
}

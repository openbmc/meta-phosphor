FILESEXTRAPATHS_prepend := "${@bb.utils.contains('DISTRO_FEATURES', \
'obmc-ubi-fs', '${THISDIR}/${PN}/base-files/df-ubi:', '', d)}"

RDEPENDS_${PN}_append_df-obmc-ubi-fs = " preinit-mounts"

do_install_append() {
    install -d ${D}/srv
}

do_install_append() {
    sed -i '/pam_systemd.so/d' ${D}${sysconfdir}/pam.d/common-session
}

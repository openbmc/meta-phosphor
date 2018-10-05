
do_install_append() {
        install -m 0644 ${WORKDIR}/nslcd.conf ${D}${sysconfdir}/nslcd.conf.default
}

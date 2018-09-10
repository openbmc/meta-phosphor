FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += " file://pam.d/common-account \
             file://pam.d/common-auth \
             file://pam.d/common-password \
             file://pam.d/common-session \
             file://pam.d/common-session-noninteractive \
             file://pam.d/other \
            "

EXTRA_OECONF = "--with-db-uniquename=_pam \
                --includedir=${includedir}/security \
                --libdir=${libdir} \
                --disable-nis \
                --disable-regenerate-docu \
                --disable-prelude"

FILES_${PN} = "${libdir}/lib*${SOLIBS}"
FILES_${PN}-dev += "${libdir}/security/*.la ${libdir}/*.la ${libdir}/lib*${SOLIBSDEV}"

python populate_packages_prepend () {
    def pam_plugin_append_file(pn, dir, file):
        nf = os.path.join(dir, file)
        of = d.getVar('FILES_' + pn)
        if of:
            nf = of + " " + nf
        d.setVar('FILES_' + pn, nf)

    def pam_plugin_hook(file, pkg, pattern, format, basename):
        pn = d.getVar('PN')
        libpam_suffix = d.getVar('libpam_suffix')

        rdeps = d.getVar('RDEPENDS_' + pkg)
        if rdeps:
            rdeps = rdeps + " " + pn + "-" + libpam_suffix
        else:
            rdeps = pn + "-" + libpam_suffix
        d.setVar('RDEPENDS_' + pkg, rdeps)

        provides = d.getVar('RPROVIDES_' + pkg)
        if provides:
            provides = provides + " " + pkg + "-" + libpam_suffix
        else:
            provides = pkg + "-" + libpam_suffix
        d.setVar('RPROVIDES_' + pkg, provides)

    mlprefix = d.getVar('MLPREFIX') or ''
    dvar = d.expand('${WORKDIR}/package')
    pam_libdir = d.expand('${libdir}/security')
    pam_sbindir = d.expand('${sbindir}')
    pam_filterdir = d.expand('${libdir}/security/pam_filter')
    pam_pkgname = mlprefix + 'pam-plugin%s'

    do_split_packages(d, pam_libdir, '^pam(.*)\.so$', pam_pkgname,
                      'PAM plugin for %s', hook=pam_plugin_hook, extra_depends='')
    pam_plugin_append_file('%spam-plugin-unix' % mlprefix, pam_sbindir, 'unix_chkpwd')
    pam_plugin_append_file('%spam-plugin-unix' % mlprefix, pam_sbindir, 'unix_update')
    pam_plugin_append_file('%spam-plugin-tally' % mlprefix, pam_sbindir, 'pam_tally')
    pam_plugin_append_file('%spam-plugin-tally2' % mlprefix, pam_sbindir, 'pam_tally2')
    pam_plugin_append_file('%spam-plugin-timestamp' % mlprefix, pam_sbindir, 'pam_timestamp_check')
    pam_plugin_append_file('%spam-plugin-mkhomedir' % mlprefix, pam_sbindir, 'mkhomedir_helper')
    pam_plugin_append_file('%spam-plugin-console' % mlprefix, pam_sbindir, 'pam_console_apply')
    do_split_packages(d, pam_filterdir, '^(.*)$', 'pam-filter-%s', 'PAM filter for %s', extra_depends='')
}

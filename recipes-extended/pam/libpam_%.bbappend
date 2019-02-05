FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += " file://pam.d/common-password \
             file://pam.d/common-account \
             file://pam.d/common-auth \
             file://pam.d/common-account-ldap \
             file://pam.d/common-auth-ldap \
            "

RDEPENDS_${PN}-runtime += "${MLPREFIX}pam-plugin-cracklib-${libpam_suffix} \
                           ${MLPREFIX}pam-plugin-tally2-${libpam_suffix} \
                           ${MLPREFIX}pam-plugin-pwhistory-${libpam_suffix} \
                          "

do_install_append() {
    # The libpam recipe will always add a pam_systemd.so line to
    # common-session if systemd is enabled; however systemd only
    # builds pam_systemd.so if logind is enabled, and we disable
    # that package.  So, remove the pam_systemd.so line here.
    sed -i '/pam_systemd.so/d' ${D}${sysconfdir}/pam.d/common-session
 
    if ${@bb.utils.contains('DISTRO_FEATURES','ldap','true','false',d)}; then
	install -d ${D}${sysconfdir}/pam.d/
	install -m 0644 ${WORKDIR}/pam.d/common-auth-ldap ${D}${sysconfdir}/pam.d/common-auth
	install -m 0644 ${WORKDIR}/pam.d/common-account-ldap ${D}${sysconfdir}/pam.d/common-account
    fi
    rm -rf ${D}${sysconfdir}/pam.d/common-auth-ldap ${D}${sysconfdir}/pam.d/common-account-ldap
}

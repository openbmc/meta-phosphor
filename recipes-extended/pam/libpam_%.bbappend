FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += " file://pam.d/common-password \
             file://pam.d/common-account \
             file://pam.d/common-auth \
             file://pam.d/common-session \
            "

RDEPENDS_${PN} += "pam-passwdqc"

RDEPENDS_${PN}-runtime += "${MLPREFIX}pam-plugin-faillock-${libpam_suffix} \
                           ${MLPREFIX}pam-plugin-pwhistory-${libpam_suffix} \
                           ${MLPREFIX}pam-plugin-succeed-if-${libpam_suffix} \
                           ${MLPREFIX}pam-plugin-localuser-${libpam_suffix} \
                          "

do_install_append() {
    # The libpam recipe will always add a pam_systemd.so line to
    # common-session if systemd is enabled; however systemd only
    # builds pam_systemd.so if logind is enabled, and we disable
    # that package.  So, remove the pam_systemd.so line here.
    sed -i '/pam_systemd.so/d' ${D}${sysconfdir}/pam.d/common-session

    # Tell systemd to create the /run/faillock directory needed for the
    # Linux-PAM faillock module and command.
    install -d ${D}${sysconfdir}/tmpfiles.d
    echo "d /run/faillock 755 root root - -" \
         > ${D}${sysconfdir}/tmpfiles.d/libpam.conf
}

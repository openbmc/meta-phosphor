SUMMARY = "Phosphor certificate manager configuration for an nslcd authority service"

PR = "r1"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

RDEPENDS_${PN} = "phosphor-certificate-manager"

inherit allarch \
        obmc-phosphor-systemd


SYSTEMD_SERVICE_${PN} = "phosphor-certificate-manager-authority@.service"

pkg_postinst_${PN}() {
	LINK="$D$systemd_system_unitdir/multi-user.target.wants/phosphor-certificate-manager-authority@0.service"
	TARGET="../phosphor-certificate-manager-authority@.service"
	mkdir -p $D$systemd_system_unitdir/multi-user.target.wants
	ln -s $TARGET $LINK
}

pkg_prerm_${PN}() {
	LINK="$D$systemd_system_unitdir/multi-user.target.wants/phosphor-certificate-manager-authority@0.service"
	rm $LINK
}

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PACKAGECONFIG_append_nuvoton = " static-bmc reboot-update"
PACKAGECONFIG_append_nuvoton = " nuvoton-lpc"

#PACKAGECONFIG_append_nuvoton = " nuvoton-p2a-mbox"
#PACKAGECONFIG_append_nuvoton = " nuvoton-p2a-vga"

IPMI_FLASH_BMC_ADDRESS_nuvoton = "${NUVOTON_FLASH_LPC}"

SRC_URI_nuvoton += "file://bmc-verify.sh"
SRC_URI_nuvoton += "file://phosphor-ipmi-flash-bmc-verify.service"

do_install_append_nuvoton() {
	install -d ${D}/${sbindir}
	install -m 0755 -D ${WORKDIR}/bmc-verify.sh ${D}/${sbindir}/bmc-verify.sh

	install -d ${D}${systemd_unitdir}/system/
	install -m 0644 ${WORKDIR}/phosphor-ipmi-flash-bmc-verify.service ${D}${systemd_unitdir}/system/
}

SYSTEMD_SERVICE_${PN}_append_nuvoton = " phosphor-ipmi-flash-bmc-verify.service"

pkg_postinst_${PN}_nuvoton() {
	LINK="$D$systemd_system_unitdir/phosphor-ipmi-flash-bmc-verify.wants/phosphor-ipmi-flash-bmc-verify.service"
	TARGET="../phosphor-ipmi-flash-bmc-verify.service"
	mkdir -p $D$systemd_system_unitdir/phosphor-ipmi-flash-bmc-verify.wants
	ln -s $TARGET $LINK
}

pkg_prerm_${PN}_nuvoton() {
	LINK="$D$systemd_system_unitdir/phosphor-ipmi-flash-bmc-verify.wants/phosphor-ipmi-flash-bmc-verify.service"
	rm $LINK
}


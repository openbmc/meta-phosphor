SUMMARY = "Phosphor Power services installation"
PR = "r1"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

inherit allarch

RDEPENDS_${PN} += "phosphor-power"

OBMC_POWER_SUPPLY_INSTANCES ?= "0"

ALLOW_EMPTY_${PN} = "1"

pkg_postinst_${PN}() {
	mkdir -p $D$systemd_system_unitdir/obmc-chassis-poweron@0.target.requires
	mkdir -p $D$systemd_system_unitdir/obmc-chassis-poweron@0.target.wants
	mkdir -p $D$systemd_system_unitdir/multi-user.target.requires

	LINK="$D$systemd_system_unitdir/obmc-chassis-poweron@0.target.wants/pseq-monitor.service"
	TARGET="../pseq-monitor.service"
	ln -s $TARGET $LINK

	LINK="$D$systemd_system_unitdir/obmc-chassis-poweron@0.target.wants/pseq-monitor-pgood.service"
	TARGET="../pseq-monitor-pgood.service"
	ln -s $TARGET $LINK

	for inst in $OBMC_POWER_SUPPLY_INSTANCES; do
		LINK="$D$systemd_system_unitdir/multi-user.target.requires/power-supply-monitor@$inst.service"
		TARGET="../power-supply-monitor@.service"
		ln -s $TARGET $LINK
	done

	CHASSIS_INSTANCES="${@d.getVar('OBMC_CHASSIS_INSTANCES') or ''}"
	for i in $CHASSIS_INSTANCES; do
		# Link each instance of phosphor-regulators against multi-user requires target
		LINK="$D$systemd_system_unitdir/multi-user.target.requires/phosphor-regulators@$i.service"
		TARGET="../phosphor-regulators@.service"
		ln -s $TARGET $LINK

		# Link each instance of phosphor-regulators-config against obmc-chassis-poweron requires target
		LINK="$D$systemd_system_unitdir/obmc-chassis-poweron@$i.target.requires/phosphor-regulators-config@$i.service"
		TARGET="../phosphor-regulators-config@.service"
		ln -s $TARGET $LINK
	done
}

pkg_prerm_${PN}() {
	LINK="$D$systemd_system_unitdir/obmc-chassis-poweron@0.target.wants/pseq-monitor.service"
	rm $LINK
	LINK="$D$systemd_system_unitdir/obmc-chassis-poweron@0.target.wants/pseq-monitor-pgood.service"
	rm $LINK
	for inst in $OBMC_POWER_SUPPLY_INSTANCES; do
		LINK="$D$systemd_system_unitdir/multi-user.target.requires/power-supply-monitor@$inst.service"
		rm $LINK
	done

	CHASSIS_INSTANCES="${@d.getVar('OBMC_CHASSIS_INSTANCES') or ''}"
	for i in $CHASSIS_INSTANCES; do
		# Remove link to phosphor-regulators from multi-user requires target
		rm "$D$systemd_system_unitdir/multi-user.target.requires/phosphor-regulators@$i.service"

		# Remove link to phosphor-regulators-config from obmc-chassis-poweron requires target
		rm "$D$systemd_system_unitdir/obmc-chassis-poweron@$i.target.requires/phosphor-regulators-config@$i.service"
	done
}

pkg_postinst_${PN}_append() {

    mkdir -p $D$systemd_system_unitdir/obmc-host-shutdown@0.target.requires
    LINK="$D$systemd_system_unitdir/obmc-host-shutdown@0.target.requires/pldmSoftPowerOff.service"
    TARGET="../pldmSoftPowerOff.service"
    ln -s $TARGET $LINK

    mkdir -p $D$systemd_system_unitdir/obmc-host-warm-reboot@0.target.requires
    LINK="$D$systemd_system_unitdir/obmc-host-warm-reboot@0.target.requires/pldmSoftPowerOff.service"
    TARGET="../pldmSoftPowerOff.service"
    ln -s $TARGET $LINK
}

pkg_prerm_${PN}_append() {

    LINK="$D$systemd_system_unitdir/obmc-host-shutdown@0.target.requires/pldmSoftPowerOff.service"
    rm $LINK

    LINK="$D$systemd_system_unitdir/obmc-host-warm-reboot@0.target.requires/pldmSoftPowerOff.service"
    rm $LINK
}

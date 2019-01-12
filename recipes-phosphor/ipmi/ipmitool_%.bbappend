FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

DEPENDS += "systemd"

SRC_URI += "file://0001-plugins-Add-a-backend-for-the-OpenBMC-dbus-interface.patch \
            file://0002-plugins-dbus-Split-netfn-from-lun-and-ccode-from-dat.patch \
            file://0003-plugins-dbus-fix-missing-semicolon.patch \
            file://0004-plugins-dbus-Fix-ccode-response.patch \
            file://0005-plugins-dbus-Use-default-dbus-connection.patch \
            file://0006-if-no-interface-is-specified-use-the-dbus-interface-.patch \
            "

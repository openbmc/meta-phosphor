SUMMARY = "Phosphor PID Fan Control"
DESCRIPTION = "Fan Control"
HOMEPAGE = "github.com/openbmc/phosphor-pid-control"
PR = "r1"
PV = "0.1+git${SRCPV}"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

inherit flto-automake pkgconfig

inherit phosphor-pid-control
inherit obmc-phosphor-ipmiprovider-symlink

S = "${WORKDIR}/git"
SRC_URI = "git://github.com/openbmc/phosphor-pid-control"
SRCREV = "90e9dbcae6c1df3127dd3de41f9d1e1b5a438828"

# Each platform will need a service file that starts
# at an appropriate time per system.  For instance, if
# your system relies on passive dbus for fans or other
# sensors then it may be prudent to wait for all of them.

DEPENDS += "autoconf-archive-native"
DEPENDS += "sdbusplus"
DEPENDS += "phosphor-logging"
DEPENDS += "phosphor-dbus-interfaces"
DEPENDS += "libevdev"
DEPENDS += "nlohmann-json"
DEPENDS += "cli11"

# We depend on this to be built first so we can build our providers.
DEPENDS += "phosphor-ipmi-host"

FILES_${PN} = "${sbindir}/swampd ${sbindir}/setsensor"

# The following installs the OEM IPMI handler for the fan controls.
FILES_${PN}_append = " ${libdir}/ipmid-providers/lib*${SOLIBS}"
FILES_${PN}_append = " ${libdir}/host-ipmid/lib*${SOLIBS}"
FILES_${PN}_append = " ${libdir}/net-ipmid/lib*${SOLIBS}"
FILES_${PN}-dev_append = " ${libdir}/ipmid-providers/lib*${SOLIBSDEV} ${libdir}/ipmid-providers/*.la"

HOSTIPMI_PROVIDER_LIBRARY += "libmanualcmds.so"

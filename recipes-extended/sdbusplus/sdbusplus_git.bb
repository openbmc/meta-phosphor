SUMMARY = "C++ bindings for systemd dbus APIs"
DESCRIPTION = "C++ bindings for systemd dbus APIs."

inherit meson
include sdbusplus-rev.inc

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e3fc50a88d0a364313df4b21ef20c29e"

S = "${WORKDIR}/git"

DEPENDS += "systemd"

EXTRA_OEMESON += "-Dexamples=disabled"
EXTRA_OEMESON += "-Dtests=disabled"

SUMMARY = "Merge machine and distro options to create a basic machine task/package"
PR = "r1"

inherit packagegroup

PROVIDES = "${PACKAGES}"
PACKAGES = " \
    packagegroup-obmc-base \
    "

RDEPENDS_packagegroup-obmc-base = " \
    "

SUMMARY = "Merge machine and distro options to create a basic machine task/package"
PR = "r1"

inherit packagegroup

PROVIDES = "${PACKAGES}"
PACKAGES = " \
    packagegroup-obmc-base \
    ${@bb.utils.contains('DISTRO_FEATURES', 'entity-manager', 'packagegroup-obmc-base-entity-manager', '', d)} \
    "

RDEPENDS_packagegroup-obmc-base = " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'entity-manager', 'packagegroup-obmc-base-entity-manager', '', d)} \
    "

SUMMARY_packagegroup-obmc-base-entity-manager = "Entity Manager support"
RDEPENDS_packagegroup-obmc-base-entity-manager = " \
    entity-manager \
    "

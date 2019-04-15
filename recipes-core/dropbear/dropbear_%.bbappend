FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRC_URI += "file://dropbearkey.service \
            file://0001-Add-default_options.h-patch-for-OpenBMC-support.patch \
            "

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRC_URI += "file://dropbearkey.service \
            file://localoptions.h"

# OpenBMC configures the dropbear ssh server via localoptions.h,
# copying them into the build directory per
# https://github.com/mkj/dropbear/blob/master/INSTALL.
# For details, see default_options.h in the dropbear project.
# Upstream status: inappropriate for Yocto/poky [configuration].
do_configure_extra() {
    install -m 0644 ${WORKDIR}/localoptions.h ${B}
}
addtask configure_extra after do_configure before do_compile

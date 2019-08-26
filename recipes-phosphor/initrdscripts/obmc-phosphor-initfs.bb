SUMMARY = "Phosphor OpenBMC pre-init scripts"
DESCRIPTION = "Phosphor OpenBMC filesystem mount reference implementation."
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${PHOSPHORBASE}/COPYING.apache-2.0;md5=34400b68072d710fecd0a2940a0d1658"

PR = "r1"

inherit allarch

RDEPENDS_${PN} += "${VIRTUAL-RUNTIME_base-utils}"

S = "${WORKDIR}"
SRC_URI += "file://obmc-init.sh"
SRC_URI += "${@bb.utils.contains('DISTRO_FEATURES', 'obmc-ext4-fs', '', 'file://obmc-shutdown.sh', d)}"
SRC_URI += "${@bb.utils.contains('DISTRO_FEATURES', 'obmc-ext4-fs', '', 'file://obmc-update.sh', d)}"
SRC_URI += "${@bb.utils.contains('DISTRO_FEATURES', 'obmc-ext4-fs', '', 'file://whitelist', d)}"

do_install() {
    if ! ${@bb.utils.contains('DISTRO_FEATURES', 'obmc-ext4-fs', 'true', 'false', d)}; then
        for f in init-download-url init-options
        do
            if test -e $f
            then
                install -m 0755 ${WORKDIR}/$f ${D}/$f
            fi
        done
        install -m 0755 ${WORKDIR}/obmc-shutdown.sh ${D}/shutdown
        install -m 0755 ${WORKDIR}/obmc-update.sh ${D}/update
        install -m 0644 ${WORKDIR}/whitelist ${D}/whitelist
    fi

    install -m 0755 ${WORKDIR}/obmc-init.sh ${D}/init
    install -d ${D}/dev
    mknod -m 622 ${D}/dev/console c 5 1
}

FILES_${PN} += " /init /dev "
FILES_${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'obmc-ext4-fs', '', ' /shutdown /update /whitelist ', d)}"
FILES_${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'obmc-ext4-fs', '', ' /init-options /init-download-url ', d)}"

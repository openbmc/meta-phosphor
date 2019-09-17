UBOOT_BINARY := "u-boot.${UBOOT_SUFFIX}"
BOOTBLOCK = "bootblock.bin"
FLASHTOOL_SUFFIX = "full"

UBOOT_SUFFIX_append = ".${FLASHTOOL_SUFFIX}"

FIU0_DRD_CFG ?= "030111BC"

# Prepare the Bootblock and U-Boot images using npcm7xx-flashtool
do_prepare_bootloaders() {
	create_image --uboot --fiu0_drd_cfg=${FIU0_DRD_CFG} \
			${DEPLOY_DIR_IMAGE}/${UBOOT_BINARY} \
			${DEPLOY_DIR_IMAGE}/${UBOOT_BINARY}.${FLASHTOOL_SUFFIX}

	create_image --bootblock --fiu0_drd_cfg=${FIU0_DRD_CFG} \
			${DEPLOY_DIR_IMAGE}/${BOOTBLOCK} \
			${DEPLOY_DIR_IMAGE}/${BOOTBLOCK}.${FLASHTOOL_SUFFIX}
}

do_prepare_bootloaders[depends] += " \
    npcm7xx-bootblock:do_deploy \
    npcm7xx-flashtool-native:do_populate_sysroot \
    "

addtask do_prepare_bootloaders \
    before do_generate_static do_generate_static_tar do_generate_ubi_tar \
    after do_generate_rwfs_static

# Include the Bootblock in the final static image
python do_generate_static_append() {
    _append_image(os.path.join(d.getVar('DEPLOY_DIR_IMAGE', True),
                               d.getVar('BOOTBLOCK') + '.' +
                               d.getVar('FLASHTOOL_SUFFIX', True)),
                  int(d.getVar('FLASH_BB_OFFSET', True)),
                  int(d.getVar('FLASH_UBOOT_OFFSET', True)))
}

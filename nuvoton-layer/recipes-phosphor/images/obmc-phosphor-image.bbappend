EXTRA_DEPENDS = ""

EXTRA_DEPENDS_npcm7xx = " \
    npcm7xx-bootblock:do_deploy \
    npcm7xx-bingo-native:do_populate_sysroot \
    "

UBOOT_BINARY := "u-boot.${UBOOT_SUFFIX}"

BOOTBLOCK = "Poleg_bootblock.bin"


FULL_SUFFIX = "full"
MERGED_SUFFIX = "merged"

UBOOT_SUFFIX_append_npcm7xx = ".${MERGED_SUFFIX}"


do_prepare_bootloaders() {

}


# Prepare the Bootblock and U-Boot images using npcm7xx-bingo

do_prepare_bootloaders_npcm7xx() {

    currdir=`pwd`
    cd ${STAGING_DIR_NATIVE}/${bindir}

	cp ${DEPLOY_DIR_IMAGE}/${BOOTBLOCK} ${STAGING_DIR_NATIVE}/${bindir}/
	bingo BootBlockAndHeader_EB.xml \
			-o ${DEPLOY_DIR_IMAGE}/${BOOTBLOCK}.${FULL_SUFFIX}

	cp ${DEPLOY_DIR_IMAGE}/${UBOOT_BINARY} ${STAGING_DIR_NATIVE}/${bindir}/
	bingo UbootHeader_EB.xml \
			-o ${DEPLOY_DIR_IMAGE}/${UBOOT_BINARY}.${FULL_SUFFIX}

	cp ${DEPLOY_DIR_IMAGE}/${BOOTBLOCK}.${FULL_SUFFIX} ${STAGING_DIR_NATIVE}/${bindir}/
	cp ${DEPLOY_DIR_IMAGE}/${UBOOT_BINARY}.${FULL_SUFFIX} ${STAGING_DIR_NATIVE}/${bindir}/
	bingo mergedBootBlockAndUboot.xml \
			-o ${DEPLOY_DIR_IMAGE}/${UBOOT_BINARY}.${MERGED_SUFFIX}

    cd $currdir
}


do_prepare_bootloaders[depends] += "${EXTRA_DEPENDS}"


addtask do_prepare_bootloaders before do_generate_static after do_generate_rwfs_static


# Include the full bootblock and u-boot in the final static image

python do_generate_static_append_npcm7xx() {

    _append_image(os.path.join(d.getVar('DEPLOY_DIR_IMAGE', True),
                               'u-boot.%s' % d.getVar('UBOOT_SUFFIX',True)),
                  int(d.getVar('FLASH_UBOOT_OFFSET', True)),
                  int(d.getVar('FLASH_KERNEL_OFFSET', True)))
}

do_make_ubi_append_npcm7xx() {

	# Concatenate the uboot and ubi partitions
	mk_nor_image ${IMGDEPLOYDIR}/${IMAGE_NAME}.ubi.mtd ${FLASH_SIZE}
	dd bs=1k conv=notrunc seek=${FLASH_UBOOT_OFFSET} \
		if=${DEPLOY_DIR_IMAGE}/u-boot.${UBOOT_SUFFIX}.${FULL_SUFFIX} \
		of=${IMGDEPLOYDIR}/${IMAGE_NAME}.ubi.mtd
}

do_make_ubi[depends] += "${PN}:do_prepare_bootloaders"

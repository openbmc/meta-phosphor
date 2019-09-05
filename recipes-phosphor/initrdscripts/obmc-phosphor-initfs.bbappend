FILESEXTRAPATHS_prepend_df-obmc-ext4-fs := "${THISDIR}/files/df-ext4:"
RDEPENDS_${PN}_append_df-obmc-ext4-fs = " e2fsprogs-mke2fs lvm2"

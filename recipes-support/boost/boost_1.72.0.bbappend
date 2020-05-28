FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# added to due boost bug see:
# https://github.com/boostorg/process/issues/116

SRC_URI += "file://0001-added-typedef-executor_type.patch"


FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRC_URI += "file://0001-json-Use-int-long.__str__-to-convert-subclasses.patch"

require wsgiref-${PYTHON_MAJMIN}-manifest.inc

PROVIDES_prepend = "${PN}-spwd "
PACKAGES_prepend = "${PN}-spwd "

SUMMARY_${PN}-spwd = "Shadow database support"
RDEPENDS_${PN}-spwd = "${PN}-core"
FILES_${PN}-spwd= " \
        ${libdir}/python${PYTHON_MAJMIN}/lib-dynload/spwd.so \
        ${libdir}/python${PYTHON_MAJMIN}/lib-dynload/grp.so \
        "
# Remove all python .py files from python recipe. Only the .pyc
# files are required. Only do this if the openbmc-phosphor-tiny
# distro feature is enabled
do_install_append_openbmc-phosphor-tiny() {
    rm -f ${D}${libdir}/python${PYTHON_MAJMIN}/*.py
    rm -f ${D}${libdir}/python${PYTHON_MAJMIN}/*/*.py
    rm -f ${D}${libdir}/python${PYTHON_MAJMIN}/*/*/*.py
    rm -f ${D}${libdir}/python${PYTHON_MAJMIN}/*/*/*/*.py
    # Remove all files related to tests
    rm -rf ${D}${libdir}/python${PYTHON_MAJMIN}/test/*
    rm -rf ${D}${libdir}/python${PYTHON_MAJMIN}/*/test/*
    rm -rf ${D}${libdir}/python${PYTHON_MAJMIN}/*/*/test/*
    rm -rf ${D}${libdir}/python${PYTHON_MAJMIN}/*/*/*/test/*
    # This file required for a patch
    cp ${WORKDIR}/recipe-sysroot-native/${libdir}/python${PYTHON_MAJMIN}/_sysconfigdata.py ${D}${libdir}/python${PYTHON_MAJMIN}/
}

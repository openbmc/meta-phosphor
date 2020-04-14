inherit update-alternatives

ALTERNATIVE_${PN}-core += "python"
ALTERNATIVE_LINK_NAME[python] = "${bindir}/python"
ALTERNATIVE_TARGET[python] = "${bindir}/python3"

# Even though python3 is built with --without-ensurepip, it still installs
# a large, compressed version of pip.  Remove it to free up the space.
do_install_append_class-target() {
    rm -rf ${D}${libdir}/python${PYTHON_MAJMIN}/ensurepip
}

# Remove all python .py files from python recipe. Only the .pyc
# files are required. Only do this if the openbmc-phosphor-tiny
# distro feature is enabled
do_install_append_openbmc-phosphor-tiny() {
    # The _sysconfigdata.py is a system configuration file generated
    # during build time. It's used in the yocto packaging process so
    # it is required to remain in the image.
    find ${D}${libdir}/python${PYTHON_MAJMIN} -name \*.py ! -name _sysconfigdata.py -exec rm {} \;
}

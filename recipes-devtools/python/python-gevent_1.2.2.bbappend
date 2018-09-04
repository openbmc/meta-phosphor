# Remove all python .py files from gevent recipe. Only the .pyc
# files are required. Only do if openbmc-phosphor-tiny distro
# feature is enabled.
do_install_append_openbmc-phosphor-tiny() {
    rm -f ${D}/${PYTHON_SITEPACKAGES_DIR}/gevent/*.py
}

# Remove all python .py files from gevent recipe. Only the .pyc
# files are required.
do_install_append() {
    rm -f ${D}/${PYTHON_SITEPACKAGES_DIR}/gevent/*.py
}

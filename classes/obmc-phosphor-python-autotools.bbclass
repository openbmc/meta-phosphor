inherit obmc-phosphor-utils
inherit pythonnative

DEPENDS += "python"

export BUILD_SYS
export HOST_SYS
export STAGING_INCDIR
export STAGING_LIBDIR

PYTHON_AUTOTOOLS_PACKAGE ?= "${PN}"

python() {
    for pkg in listvar_to_list(d, 'PYTHON_AUTOTOOLS_PACKAGE'):
        set_append(d, 'FILES_%s' % pkg,
                   d.getVar('PYTHON_SITEPACKAGES_DIR', True))
}

# In order to facilitate packages that use python3native, but also
# depend on python2 scripts we need to replace the #! to be nativepython
# instead of just python.  Without this, `which python` points to the
# host's python, which is not the one where required modules would be
# installed.
do_install_append_class-native() {
    for i in ${D}${bindir}/* ${D}${sbindir}/*; do
        if [ -f "$i" ]; then
            sed -i -e s:env\ python:env\ nativepython:g $i
        fi
    done
}

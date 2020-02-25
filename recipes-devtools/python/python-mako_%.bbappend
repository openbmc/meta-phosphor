# python-mako installs /usr/bin/mako-render for both python2 and python3,
# which causes a bitbake QA failure.  Put this into a separate package for
# the python2 version, since nobody in openbmc uses this anyhow.
#
# Once we are done with python2, we can delete this.

PACKAGE_BEFORE_PN += "${PN}-render"
FILES_${PN}-render += "${bindir}/mako-render"

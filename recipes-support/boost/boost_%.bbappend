# context and coroutine really are available on arm, despite
# the comment otherwise in the main boost recipe
BOOST_LIBS_append_arm = " context coroutine"
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRC_URI_append_arm = "\
    file://context-asm-arm-assume-aacps.patch \
	"

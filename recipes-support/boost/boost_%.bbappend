# context and coroutine really are available on arm, despite
# the comment otherwise in the main boost recipe
BOOST_LIBS_append_arm = " context coroutine"
BJAM_OPTS_append_arm = " architecture=arm abi=aapcs "

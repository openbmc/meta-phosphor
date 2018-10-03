PACKAGECONFIG_append = " openssl"
PACKAGECONFIG_remove = "gcrypt gnutls png sdl zlib"

TARGET_CXXFLAGS += " -Dflto"

do_install_append() {
    rm -rf ${D}${libdir}/libvncclient*
}

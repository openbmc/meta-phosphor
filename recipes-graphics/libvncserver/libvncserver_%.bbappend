PACKAGECONFIG_append = " openssl"
PACKAGECONFIG_remove = "gcrypt gnutls png sdl"

FILESEXTRAPATHS_append := ":${THISDIR}/${PN}"
SRC_URI += "file://0001-Add-a-hooking-function-to-deliver-rfbFramebufferUpda.patch"

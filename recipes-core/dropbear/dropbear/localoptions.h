// Configure dropbear ssh server settings for OpenBMC
// Inappropriate for upstream to Yocto/poky
// Configured for DROPBEAR_2019.77, applicable for later releases

// Disable ciphers: CBC mode
#define DROPBEAR_ENABLE_CBC_MODE 0

// Disable MAC algorithms:
#define DROPBEAR_SHA1_HMAC 0
#define DROPBEAR_SHA1_96_HMAC 0

// Disable KEX algorithms: diffie-hellman SHA1 and GROUP1
#define DROPBEAR_DH_GROUP14_SHA1 0
#define DROPBEAR_DH_GROUP1 0

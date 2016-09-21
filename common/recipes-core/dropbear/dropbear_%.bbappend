inherit obmc-phosphor-avahi

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://dropbearkey.service \
	    file://0001-dropbear-Add-c-command-option-to-force-a-specific-co.patch"

AVAHI_SERVICES += "sshd sftp"
AVAHI_SVC_TYPE_sshd = "tcp"
AVAHI_SVC_PORT_sshd = "21"
AVAHI_SVC_TYPE_sftp = "tcp"
AVAHI_SVC_PORT_sftp = "22"

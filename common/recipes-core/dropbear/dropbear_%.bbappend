inherit obmc-phosphor-discovery-service

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://dropbearkey.service \
	    file://0001-dropbear-Add-c-command-option-to-force-a-specific-co.patch"

AVAHI_SERVICES += "ssh sftp"
AVAHI_SVC_TYPE_ssh = "_ssh._tcp"
AVAHI_SVC_PORT_ssh = "21"
AVAHI_SVC_TYPE_sftp = "_sftp._tcp"
AVAHI_SVC_PORT_sftp = "22"

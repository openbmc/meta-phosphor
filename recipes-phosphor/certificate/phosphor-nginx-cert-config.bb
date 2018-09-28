SUMMARY = "Phosphor certificate manager configuration for an nginx certificate"

PR = "r1"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

RRECOMMENDS_${PN} = "phosphor-certificate-manager"

inherit allarch
inherit obmc-phosphor-systemd

SYSTEMD_SERVICE_${PN} = ""
SYSTEMD_ENVIRONMENT_FILE_${PN} = "obmc/cert/nginx"
SYSTEMD_LINK_${PN} = "../phosphor-certificate-manager@.service:${SYSTEMD_DEFAULT_TARGET}.wants/phosphor-certificate-manager@nginx.service"

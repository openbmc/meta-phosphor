inherit obmc-phosphor-utils
PACKAGES += "${PN}-avahi"

python() {

    pn = d.getVar('PN', True)
    syscnfdir = d.getVar('sysconfdir',True)
    dest_dir = d.getVar('D',True)
    AVAHI_ENABLED = bb.utils.contains('DISTRO_FEATURES', 'avahi','true','false', d)
    d.setVar("AVAHI_ENABLED",AVAHI_ENABLED)
    if AVAHI_ENABLED == 'true':
        set_append(d, 'RRECOMMENDS_%s' % pn, 'avahi-daemon')
        set_append(d, 'IMAGE_INSTALL', 'avahi-daemon')
        set_append(d, 'FILES_%s-avahi' % pn, syscnfdir+'/avahi/services/*.service')
        set_append(d, 'AVAHI_SERVICES_DIR', dest_dir+syscnfdir+'/avahi/services/')
}

python discovery_services_postinstall() {

    def register_service_avahi(d, service_name, service_type, service_port):
        service_dir = d.getVar('AVAHI_SERVICES_DIR', True)
        service_file = service_dir + service_name + ".service"
        # bb.warn("Service File %s" % service_file)
        if not os.path.exists(service_dir):
            os.makedirs(service_dir)
            with open(service_file, 'w') as fd:
                fd.write('<?xml version="1.0" ?>\n')
                fd.write('<!DOCTYPE service-group SYSTEM "avahi-service.dtd">\n')
                fd.write('<service-group>\n')
                fd.write('        <name>"%s"</name>\n' % service_name)
                fd.write('        <service>\n')
                fd.write('                <type>"%s"</type>\n' % service_type)
                fd.write('                <port>"%s"</port>\n' % service_port)
                fd.write('        </service>\n')
                fd.write('</service-group>\n')
                fd.close()

    def register_services(d):
        for service in listvar_to_list(d, 'REGISTERED_SERVICES'):
            svc_info = service.split(":")
            if len(svc_info) != 3:
                continue
            svc_name = svc_info[0]
            svc_type = svc_info[1]
            svc_port = svc_info[2]
            is_avahi_enabled = d.getVar('AVAHI_ENABLED', True)
            if is_avahi_enabled == 'true':
               #  bb.warn("svc_port=%s" % svc_port )
                register_service_avahi(d, svc_name, svc_type, svc_port)

    register_services(d)
}

do_install[postfuncs] += "discovery_services_postinstall"

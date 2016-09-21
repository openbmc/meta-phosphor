inherit pythonnative
inherit obmc-phosphor-utils
DATADIR = "${D}${sysconfdir}/avahi/services/"

python avahi_postinstall() {

    def register(d,service_name,service_type,service_port,target):
        if target == "AVAHI":
            service_dir = d.getVar('DATADIR',True);
            service_file = service_dir + service_name + ".service"
            bb.warn("Service File is %s" % service_file)
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

        # Register AVAHI Service
        for service in listvar_to_list(d, 'AVAHI_SERVICES'):
            svc_type = d.getVar('AVAHI_SVC_TYPE_%s'% service,True)
            svc_port = d.getVar('AVAHI_SVC_PORT_%s'% service,True)
            register(d,service,svc_type,svc_port,"AVAHI")

        # Regsiter SLP Service
        # Currently it is a no-op

        for service in listvar_to_list(d, 'SLP_SERVICES'):
            svc_type = d.getVar('SLP_SVC_TYPE_%s'% service,True)
            svc_port = d.getVar('SLP_SVC_PORT_%s'% service,True)
            register(d,service,svc_type,svc_port,"SLP")


    register_services(d)
}

do_install[postfuncs] += "avahi_postinstall"

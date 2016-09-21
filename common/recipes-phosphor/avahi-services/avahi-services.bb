inherit allarch
inherit obmc-phosphor-license
inherit obmc-phosphor-avahi


DEPENDS += "avahi"
##We can give the info of  all the services here 
## in this bb files
## or we can write bbappend or make the change in bb file

AVAHI_SERVICES += "Test1 Test2"
AVAHI_SVC_TYPE_Test1 = "tcp"
AVAHI_SVC_PORT_Test1 = "21"
AVAHI_SVC_TYPE_Test2 = "tcp"
AVAHI_SVC_PORT_Test2 = "22"




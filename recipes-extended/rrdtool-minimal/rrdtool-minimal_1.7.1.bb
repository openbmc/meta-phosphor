SUMMARY = "High performance data logging and graphing system for time series data"
HOMEPAGE = "http://oss.oetiker.ch/rrdtool/"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYRIGHT;md5=3349111ed0533471494beec99715bc9d"

DEPENDS = "zlib glib-2.0 libxml2"

SRCREV = "34e6ff6218bb0372eb545f886dec96dd3d20be47"
PV = "1.7.1"

SRC_URI = "\
    git://github.com/oetiker/rrdtool-1.x.git;branch=master;protocol=http; \
"

S = "${WORKDIR}/git"

inherit autotools-brokensep gettext pythonnative

BBCLASSEXTEND = "native"

EXTRA_AUTORECONF = "-I m4 --exclude=autopoint"

EXTRA_OECONF = " \
    --enable-shared \
    --disable-libwrap \
    --program-prefix='' \
    rd_cv_ieee_works=yes \
    --disable-ruby \
    --disable-python \
    --disable-perl \
    --disable-lua \
    --disable-tcl \
    --disable-rpath \
    --enable-nls=${USE_NLS} \
    --disable-docs \
    --disable-examples \
    --disable-rrdcached \
    --disable-rrdcgi \
    --disable-rrd_graph \
"

export STAGING_LIBDIR
export STAGING_INCDIR

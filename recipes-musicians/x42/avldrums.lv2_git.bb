SUMMARY = "Dedicated AVLDrumkits LV2 Plugin"
HOMEPAGE = "http://x42-plugins.com/x42/x42-avldrums"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

inherit pkgconfig

DEPENDS += " \
    virtual/libgl \
    libglu \
    glib-2.0 \
    pango \
    cairo \
    lv2 \
"

SRC_URI = "gitsm://github.com/x42/avldrums.lv2.git"
SRCREV = "532d62422a72dbc8a3aacfaf65ed903ff412a4ca"
PV = "0.3.4"
S = "${WORKDIR}/git"

EXTRA_OEMAKE += " \
    STRIP=echo \
    OPTIMIZATIONS= \
"

do_install() {
    oe_runmake DESTDIR=${D} PREFIX=${prefix} LV2DIR=${libdir}/lv2 install
}

FILES_${PN} += "${libdir}/lv2"

SUMMARY = "A virtual guitar amplifier"
HOMEPAGE = "http://guitarix.org/"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=384f45fb7968a0fe30622ce6160d3b69"

PV = "0.38.0"
SRC_URI = " \
    ${SOURCEFORGE_MIRROR}/project/${BPN}/${BPN}/${BPN}2-${PV}.tar.xz \
    file://0001-do-not-perform-link-test-durin-cross-build.patch \
    file://0002-Fix-build-for-musl.patch \
"
SRC_URI[md5sum] = "c533bfd7223c42a7aef0e516f5b4cc01"
SRC_URI[sha256sum] = "c709ec903b2ae653802bd9327c32a20a086e6a97e8d029c8d79f1a8445dd655b"

inherit wafold fontcache gettext

DEPENDS += " \
    gperf-native \
    intltool-native \
    boost \
    libeigen \
    avahi \
    bluez5 \
    gtkmm \
    jack \
    lilv \
    ladspa-sdk \
    libsndfile1 \
    lrdf \
    zita-resampler \
    zita-convolver \
"

EXTRA_OECONF = " \
    --disable-sse \
    --ldflags="${LDFLAGS}" \
    --no-ldconfig \
    --no-desktop-update \
    --shared-lib \
    --lib-dev \
    --install-roboto-font \
"

do_install_append() {
    # some corrections [dev-elf] - inspired by https://src.fedoraproject.org/rpms/guitarix/blob/master/f/guitarix.spec
    chmod 755 ${D}${libdir}/libgxw*.so.0.1
    rm -rf ${D}${libdir}/libgxw*.so
    ln -sf libgxwmm.so.0.1 ${D}${libdir}/libgxwmm.so
    ln -sf libgxw.so.0.1 ${D}${libdir}/libgxw.so
}

FILES_${PN} += " \
    ${datadir}/fonts \
    ${datadir}/gx_head \
    ${datadir}/ladspa \
    ${libdir}/ladspa \
    ${libdir}/lv2 \
"

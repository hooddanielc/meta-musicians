SUMMARY = "An old-school drum kit sampler"
HOMEPAGE = "http://drumkv1.sourceforge.net/"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"

DEPENDS += " \
    qtbase-native \
    qtbase \
    jack \
    lv2 \
    liblo \
    hydrogen \
"

inherit qmake5_base autotools-brokensep pkgconfig gtk-icon-cache mime

SRC_URI = " \
    ${SOURCEFORGE_MIRROR}/project/${BPN}/${BPN}/${PV}/${BPN}-${PV}.tar.gz \
    git://github.com/TuriSc/hydrogen2drumkv1.py.git;name=hydrogen2drumkv1;destsuffix=hydrogen2drumkv1 \
    file://0001-no-Qt4-find-native-qt-build-tools-by-configure-options-auto.patch \
    file://0002-Avoid-stripping.patch \
"
PV = "0.9.14"
SRC_URI[md5sum] = "129539c33be52cdc4e996ca74f8d73dc"
SRC_URI[sha256sum] = "53e1f23f175a5c9835887d97d216c4fb49ed46d2b909718feb72ff52eebc273b"

SRCREV_hydrogen2drumkv1 = "4ca8af8f1433dce33f675ae68e95429c9eed084e"

EXTRA_OECONF = " \
    --with-qmake=${OE_QMAKE_PATH_EXTERNAL_HOST_BINS}/qmake \
    --with-moc=${OE_QMAKE_PATH_EXTERNAL_HOST_BINS}/moc \
    --with-uic=${OE_QMAKE_PATH_EXTERNAL_HOST_BINS}/uic \
    --with-lupdate=${OE_QMAKE_PATH_EXTERNAL_HOST_BINS}/lupdate \
    --with-lrelease=${OE_QMAKE_PATH_EXTERNAL_HOST_BINS}/lrelease \
"

do_install_append() {
    install -d ${D}${datadir}/${BPN}/presets
    # convert hydrogen drumkits to drumkv1
    export IFS=$'\n'
    for drumkit in `find ${STAGING_DATADIR}/hydrogen/data/drumkits -name drumkit.xml`; do
        echo $drumkit
        drumkit_name=`dirname $drumkit`
        drumkit_name=`basename $drumkit_name`
        echo $drumkit_name
        python3 ${WORKDIR}/hydrogen2drumkv1/hydrogen2drumkv1.py --prefix ${datadir}/hydrogen/data/drumkits/$drumkit_name/ $drumkit ${D}${datadir}/${BPN}/presets/$drumkit_name.drumkv1
    done
}

PACKAGES =+ "${PN}-presets"

FILES_${PN} += " \
    ${datadir}/appdata \
    ${datadir}/mime \
    ${datadir}/metainfo \
    ${datadir}/icons \
    ${libdir}/lv2 \
"

FILES_${PN}-presets += "${datadir}/${BPN}/presets/"
# hydrogen-drumkits contain samples
RDEPENDS_${PN}-presets = "hydrogen-drumkits"

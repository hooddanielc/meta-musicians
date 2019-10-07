# Helper class to handle creation of lv2 turtle files

# Turtle files (*.ttl) are created during compile usually. This does not work
# for us because cross build libraries cannot be opened by build host. To get
# around we:
#
# 1. Adjust build (see do_ttl_sed) so that instead calling LV2-TTL-GENERATOR
#    the plugin file name is written to LV2_PLUGIN_INFO_FILE
# 2. Try to generate ttl with the help of qemu. This can faile for various
#    reasons: SIMD instructions not supported / qemu itself / ...
#    For files not handled properly we try:
# 3. Generate ttl-files at first boot / after package was installed


# File containing names of plugins to handle in do_compile_append 
# Line-format expected: <some-path-in-build>/<plugin>.so
LV2_PLUGIN_INFO_FILE = "${WORKDIR}/lv2-ttl-generator-data"

# File containing names of plugins to handle in do_compile_append 
# Line-format expected: <path-ontarget>/<plugin>.so
LV2_PLUGIN_POSTINST_INFO_FILE = "${LV2_PLUGIN_INFO_FILE}-postinst"

# To make ontarget postinst/prerm happen, the names of all plugins with their
# paths as installed on target a stored in a file called lv2-postinst-manifest
LV2_POSTINST_MANIFEST = "${datadir}/${BPN}/lv2-postinst-manifest"

# Path to the ttl-generator qemu will use. Since most plugins are based on dpf
# (added by git-submodule) we can set a default matchin > 80%+
LV2-TTL-GENERATOR ?= "${S}/dpf/utils/lv2_ttl_generator"

inherit qemu-ext

# override this function and execute sed (or other magic) to adjust Makefiles
# so that lv2-ttl-generator is not executed but plugin information. Same here:
# Set default matich many dpf-based plugins
do_ttl_sed() {
    sed -i 's|"$GEN" "./$FILE"|echo "`realpath  "./$FILE"`" >> ${LV2_PLUGIN_INFO_FILE}|g' ${S}/dpf/utils/generate-ttl.sh
}

do_configure_prepend() {
    do_ttl_sed
}

do_compile_prepend() {
    # remove plugin-info from previous build
    rm -f ${LV2_PLUGIN_INFO_FILE}
    rm -f ${LV2_PLUGIN_POSTINST_INFO_FILE}
}

do_compile_append() {
    if [ -e ${LV2_PLUGIN_INFO_FILE} ]; then
        # try build ttl-files with quemu
        for sofile in `sort ${LV2_PLUGIN_INFO_FILE} | uniq`; do
            cd `dirname ${sofile}`
            echo "QEMU lv2-ttl-generator for ${sofile}..."
            TTL_FAILED=""
            ${@qemu_run_binary_local(d, '${STAGING_DIR_TARGET}', '${LV2-TTL-GENERATOR}')} ${sofile} || TTL_FAILED="true"
            if [ "x${TTL_FAILED}" = "x" ]; then
                echo "Generation succeeded."
            else
                # If qemu fails: remove generated core files & prepare for postinst fallback
                echo "ERROR: for QEMU lv2_ttl_generator for ${sofile}!"
                rm -f *.core
                echo `basename $sofile` >> ${LV2_PLUGIN_POSTINST_INFO_FILE}
            fi
        done
    else
        echo
        echo "LV2_PLUGIN_INFO_FILE was not created during compilation - check do_ttl_sed() or patch postponing ttl-generation"
        echo
        exit -1
    fi
}

do_install_append() {
    # create postinst manifest
    if [ -e ${LV2_PLUGIN_POSTINST_INFO_FILE} ]; then
        install -d ${D}`dirname ${LV2_POSTINST_MANIFEST}`
        for sofile in `cat ${LV2_PLUGIN_POSTINST_INFO_FILE}`; do
            installed=`find ${D}${libdir}/lv2 -name $sofile | sed 's|${D}||g'`
            echo $installed >> ${D}${LV2_POSTINST_MANIFEST}
        done
    fi
}

pkg_postinst_ontarget_${PN}-lv2() {
    if [ -e ${LV2_POSTINST_MANIFEST} ]; then
        oldpath=`pwd`
        for sofile in `cat ${LV2_POSTINST_MANIFEST}`; do
            cd `dirname "$sofile"`
            lv2-ttl-generator "$sofile" || echo "Error: Turtle files for $sofile could not be created!"
        done
        cd $oldpath
    fi
}

pkg_prerm_${PN}-lv2() {
    if [ -e ${LV2_POSTINST_MANIFEST} ]; then
        for sofile in `cat ${LV2_POSTINST_MANIFEST}`; do
            path=`dirname "$sofile"`
            for turtle in `find $path -name '*.ttl'`; do
                rm $turtle
            done
        done
    fi
}

FILES_${PN}-lv2 += "${LV2_POSTINST_MANIFEST}"
RDEPENDS_${PN}-lv2 += "lv2-ttl-generator"
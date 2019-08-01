SUMMARY = "All music musicican packages"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit packagegroup

# unfortunately there is no way to add a comment. So folders, containing
# multiple recipes are separated by two '\' before and after. That way the
# sequence as displayed in file browser is kept more or less and avoids to
# overlook a package.

RDEPENDS_${PN} += " \
    amsynth-standalone amsynth-lv2 amsynth-vst \
    ardour5 \
    aubio \
    audio-tweaks \
    bristol \
    calf \
    carla \
    chromaprint \
    csound \
    \
    \
    lpd8editor \
    \
    \
    distrho-ports-lv2 distrho-ports-presets distrho-ports-vst \
    distrho-ports-extra-lv2 distrho-ports-extra-vst \
    dpf-plugins-ladspa dpf-plugins-lv2 dpf-plugins-vst \
    lv2-ttl-generator \
    \
    \
    drmr \
    \
    \
    ganv \
    ingen-lv2 ingen-standalone \
    jalv \
    lilv \
    lv2 \
    mda-lv2 \
    patchage \
    serd \
    sord \
    sratom \
    suil \
    \
    \
    drumgizmo \
    dgedit \
    \
    \
    dssi \
    dssi-vst \
    ftgl \
    gmidimonitor \
    guitarix \
    gxplugins.lv2 \
    helm-standalone helm-lv2 helm-vst \
    hydrogen hydrogen-drumkits \
    infamousplugins \
    \
    \
    clthreads \
    clxclient \
    zita-convolver \
    zita-resampler \
    \
    \
    ladspa-sdk \
    liblo \
    libmp4v2 \
    libsmf \
    libxmp \
    \
    \
    gigedit \
    libgig-bin \
    liblscp \
    linuxsampler-standalone linuxsampler-tools linuxsampler-dssi linuxsampler-lv2 \
    \
    \
    lmms \
    lrdf \
    lsp-plugins-standalone lsp-plugins-ladspa lsp-plugins-lv2 lsp-plugins-vst \
    mixxx \
    muse \
    nekobee \
    ninjas2-standalone ninjas2-lv2 ninjas2-vst \
    non \
    \
    \
    arty-fx \
    fabla \
    luppp \
    openav-presets \
    sorcer \
    \
    \
    polyphone \
    portmidi \
    projectm \
    qmidiarp \
    qmmp \
    remid.lv2 \
    \
    \
    drumkv1 drumkv1-presets \
    padthv1 padthv1-presets \
    qjackctl qjackctl-defconfig \
    qmidictl \
    qmidinet \
    qsampler \
    qsynth \
    qtractor qtractor-defconfig \
    samplv1 \
    synthv1 synthv1-presets \
    \
    \
    rosegarden \
    rubberband \
    setbfree \
    \
    \
    fluidsynth-dssi \
    fluidsynth-dssi-defconfig \
    hexter \
    whysynth \
    xsynth-dssi \
    \
    \
    sf-tools \
    soundfont-collection-meta \
    \
    \
    soundtouch \
    stk \
    supercollider \
    triceratops-lv2 \
    vamp-plugin-sdk \
    wolf-shaper-dssi wolf-shaper-lv2 wolf-shaper-vst \
    wolf-spectrum wolf-spectrum-lv2 wolf-spectrum-vst \
    \
    \
    avldrums.lv2 \
    darc.lv2 \
    dpl.lv2 \
    fil4.lv2 \
    meters.lv2 \
    midifilter.lv2 \
    sisco.lv2 \
    tuna.lv2 \
    \
    \
    ykchorus ykchorus-dssi ykchorus-lv2 ykchorus-vst \
    yoshimi \
    zam-plugins-standalone zam-plugins-ladspa zam-plugins-lv2 zam-plugins-vst \
    ${@bb.utils.contains("DISTRO_FEATURES", "opengl", "zyn-fusion-dssi zyn-fusion-lv2 zyn-fusion-vst zyn-fusion-standalone", "zynaddsubfx-dssi zynaddsubfx-lv2 zynaddsubfx-vst zynaddsubfx-standalone",d)} \
"


# tomahawk

# empty
# qm-dsp

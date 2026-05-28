SUMMARY = "Gst-Shark Tracers"
DESCRIPTION = "Benchmarks and profiling tools for GStreamer"
HOMEPAGE = "https://developer.ridgerun.com/wiki/index.php?title=GstShark"
SECTION = "multimedia"
LICENSE = "GPL-2.0-or-later"

LIC_FILES_CHKSUM = "file://COPYING;md5=e1caa368743492879002ad032445fa97"
COMPATIBLE_MACHINE = ".*"

DEPENDS = " \
    gstreamer1.0 \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-bad \
    "

SRCBRANCH ?= "master"

SRCREV = "${AUTOREV}"
SRCREV_FORMAT = "default_common"

SRC_URI = " \
    git://github.com/RidgeRun/gst-shark.git;protocol=https;branch=${SRCBRANCH} \
    git://gitlab.freedesktop.org/gstreamer/common.git;protocol=https;branch=master;destsuffix=git/common;name=common \
    "

S = "${WORKDIR}/git"

PACKAGECONFIG ??= ""
PACKAGECONFIG[gtk-doc] = "--enable-gtk-doc,--disable-gtk-doc,gtk-doc-native"
PACKAGECONFIG[graphviz] = "--enable-graphviz,--disable-graphviz,graphviz"

FILES:${PN} += " \
    ${libdir}/gstreamer-1.0/libgstsharktracers.so \
    "

inherit autotools gettext pkgconfig gtk-doc rr_proprietary

do_configure() {
    ${S}/autogen.sh --noconfigure
    oe_runconf
}

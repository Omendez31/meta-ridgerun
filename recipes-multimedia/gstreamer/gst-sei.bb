SUMMARY = "GStreamer SEI Plugin"
DESCRIPTION = "GStreamer plugin to add meta-data as SEI NAL units"
HOMEPAGE = "https://www.ridgerun.com/gstseimetadata"
SECTION = "multimedia"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=2207b8e5f4ab4b3a0c794e43f2002ea3"
COMPATIBLE_MACHINE = ".*"


DEPENDS = " \
    gstreamer1.0 \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-bad \
    gstreamer1.0-plugins-ugly \
    gobject-introspection-native \
    "

SRCBRANCH ?= "master"
SRCREV = "${AUTOREV}"
SRC_URI = "git://git@gitlab.ridgerun.com/ridgerun/orders/${RR_CUSTOMER_GITLAB_ORDER_DIR}/gst-sei.git;protocol=ssh;branch=${SRCBRANCH}"
S = "${WORKDIR}/git"

FILES:${PN} += " \
    ${libdir}/gstreamer-1.0/libgstsei.so \
    ${libdir}/girepository-1.0/GstSei-1.0.typelib \
    ${datadir}/gir-1.0/GstSei-1.0.gir \
    "

# Package configuration options
PACKAGECONFIG ??= ""
PACKAGECONFIG[doc] = "-Ddoc=enabled,-Ddoc=disabled,python3-hotdoc-native"
PACKAGECONFIG[examples] = "-Dexamples=enabled,-Dexamples=disabled"
PACKAGECONFIG[tests] = "-Dtests=enabled,-Dtests=disabled"

# Meson build options
EXTRA_OEMESON = "-Ddeveloper-mode=false"

# Inherit necessary classes
inherit meson pkgconfig rr_proprietary

# Package configuration
PACKAGES_DYNAMIC = "^libsei.*"
INSANE_SKIP:${PN} = "dev-so"

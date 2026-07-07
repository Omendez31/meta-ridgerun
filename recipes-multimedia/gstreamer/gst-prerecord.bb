SUMMARY = "GStreamer Pre-Record Plugin"
DESCRIPTION = "GStreamer element that pre-records data continuously into a FIFO"
HOMEPAGE = "https://developer.ridgerun.com/wiki/index.php?title=GStreamer_pre-record_element"
SECTION = "multimedia"
LICENSE = "Proprietary"

LIC_FILES_CHKSUM = "file://COPYING;md5=1887e8dfc90a84423fd31d1d45ee6718"
COMPATIBLE_MACHINE = ".*"

DEPENDS = "gstreamer1.0 gstreamer1.0-plugins-base libffi"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRCBRANCH ?= "master"
SRCREV = "${AUTOREV}"
SRC_URI = "gitsm://git@gitlab.ridgerun.com/ridgerun/orders/${RR_CUSTOMER_GITLAB_ORDER_DIR}/gst-prerecord.git;protocol=ssh;branch=${SRCBRANCH}"

S = "${WORKDIR}/git"

FILES:${PN} += "\
        ${libdir}/gstreamer-1.0/libgstprerecord.so \
        ${libdir}/gstreamer-1.0/libgstprerecord.la \
"

PACKAGECONFIG ??= ""
PACKAGECONFIG[gtk-doc] = "--enable-gtk-doc,--disable-gtk-doc,gtk-doc-native"

EXTRA_AUTORECONF += "${@bb.utils.contains('PACKAGECONFIG', 'gtk-doc', '', ' --exclude=gtkdocize', d)}"

inherit autotools pkgconfig gettext rr_proprietary

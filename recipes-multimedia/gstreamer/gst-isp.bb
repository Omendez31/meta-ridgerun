SUMMARY = "Gst-ISP plugin"
DESCRIPTION = "Gst-ISP is a GPU-accelerated GStreamer plugin for image signal processing."
HOMEPAGE = "https://www.ridgerun.com/gstisp"
SECTION = "multimedia"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=46819161aba98ab8c502e93a15713e58"
COMPATIBLE_MACHINE = "(imx-nxp-bsp|.*qcom.*)"

DEPENDS = " \
    gstreamer1.0 \
    gstreamer1.0-plugins-base \
    gst-opencl \
"

SRCBRANCH ?= "master"
SRCREV = "${AUTOREV}"
SRC_URI = "git://git@gitlab.ridgerun.com/ridgerun/orders/${RR_CUSTOMER_GITLAB_ORDER_DIR}/gst-isp.git;protocol=ssh;user=git;branch=${SRCBRANCH}"
S = "${WORKDIR}/git"

FILES:${PN} += " \
    ${libdir}/gstreamer-1.0/libgstisp.so \
    ${libdir}/libgstisp.so \
    ${libdir}/libgstisp.so.0 \
    ${libdir}/libgstisp.so.0.0.0 \
"

PACKAGECONFIG ??= ""
PACKAGECONFIG[tests] = "--enable-tests,--disable-tests"
PACKAGECONFIG[developer] = "--enable-developer,--disable-developer"

inherit autotools pkgconfig gettext rr_proprietary

do_install:append() {
    # Avoid QA error for installed-but-not-shipped static library
    rm -f ${D}${libdir}/gstreamer-1.0/libgstisp.a
}

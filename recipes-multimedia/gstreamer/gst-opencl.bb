SUMMARY = "GstOpenCL Plugin"
DESCRIPTION = "GstOpenCL is a GStreamer plugin that enables hardware-accelerated processing using OpenCL"
HOMEPAGE = "https://www.ridgerun.com/post/gstreamer-opencl-accelerated-isp"
SECTION = "multimedia"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=46819161aba98ab8c502e93a15713e58"
COMPATIBLE_MACHINE = "(imx-nxp-bsp|.*qcom.*)"

DEPENDS = " \
    gstreamer1.0 \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-bad \
"

DEPENDS:append:imx-nxp-bsp = " imx-gst1.0-plugin"

SRCBRANCH ?= "master"
SRCREV = "${AUTOREV}"

SRC_URI = "git://git@gitlab.ridgerun.com/ridgerun/orders/${RR_CUSTOMER_GITLAB_ORDER_DIR}/gst-opencl.git;protocol=ssh;user=git;branch=${SRCBRANCH}"

S = "${WORKDIR}/git"

FILES_SOLIBSDEV = ""

FILES:${PN} += " \
    ${libdir}/gstreamer-1.0/libgstopencl.so \
    ${libdir}/libgstopencl.so \
    ${libdir}/libgstopencl.so.0 \
    ${libdir}/libgstopencl.so.0.0.0 \
"

inherit autotools pkgconfig gettext rr_proprietary

do_configure() {
    ${S}/autogen.sh
    oe_runconf
}

# To avoid QA error related to package containing symlink .so
INSANE_SKIP:${PN} = "dev-so"

do_install:append() {
    # Avoid QA error for installed-but-not-shipped static library
    rm -f ${D}${libdir}/gstreamer-1.0/libgstopencl.a
}

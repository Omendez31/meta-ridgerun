SUMMARY = "GStreamer Daemon 1.0"
DESCRIPTION = "GStreamer framework for controlling audio and video streaming using TCP connection messages"
HOMEPAGE = "https://developer.ridgerun.com/wiki/index.php?title=Gstd-1.0"
SECTION = "multimedia"
LICENSE = "LGPL-2.1-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=c71b653a0f608a58cdc5693ae57126bc"
COMPATIBLE_MACHINE = ".*"

DEPENDS = " \
    gstreamer1.0 \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-bad \
    gstreamer1.0-rtsp-server \
    gtk-doc-native \
    json-glib \
    libdaemon \
    jansson \
    libsoup-2.4 \
    libedit \
    "

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRCBRANCH ?= "master"
SRCREV = "${AUTOREV}"
SRC_URI = "git://github.com/RidgeRun/gstd-1.x.git;protocol=https;branch=${SRCBRANCH} \
           file://gstd-yocto-fixes.patch \
"
S = "${WORKDIR}/git"

PACKAGECONFIG ??= ""
PACKAGECONFIG[gtk-doc] = "--enable-gtk-doc,--disable-gtk-doc"

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE:${PN} = "gstd.service"

inherit autotools gettext pkgconfig setuptools3 systemd rr_proprietary

# Python API path
SETUPTOOLS_SETUP_PATH = "${S}/libgstc/python"

EXTRA_OECONF = " \
    --with-gstd-runstatedir=${base_prefix}/run \
    --with-gstd-logstatedir=${localstatedir}/log \
    --with-gstd-systemddir=${systemd_unitdir}/system \
    "

do_configure() {
    cd ${S}
    ./autogen.sh --noconfigure
    cd ${B}
    oe_runconf
}

do_compile() {
    autotools_do_compile
    setuptools3_do_compile
}

do_install() {
    autotools_do_install
    # Install Python API
    setuptools3_do_install
    # Runtime state/log directories must not be shipped in the package.
    # They are created at runtime by the init/system service.
    rm -rf ${D}${base_prefix}/run
    rm -rf ${D}${localstatedir}/run
    rm -rf ${D}${localstatedir}/log
    rm -rf ${D}${localstatedir}/volatile/log
    if [ -d ${D}${localstatedir}/volatile ]; then
        rmdir --ignore-fail-on-non-empty ${D}${localstatedir}/volatile
    fi
}

FILES:${PN} += " \
    ${systemd_unitdir}/system/gstd-check-user-xenv.sh \
    ${systemd_unitdir}/system/gstd.service \
    "

# Split the Python API to a separate package
PACKAGES += "${PN}-python"

# Make the Python API depend on the main package
RDEPENDS:${PN}-python = "${PN} ${PYTHON_PN}-core"
FILES:${PN}-python = "${PYTHON_SITEPACKAGES_DIR}"

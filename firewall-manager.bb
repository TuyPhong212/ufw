SUMMARY = "Network Firewall"
DESCRIPTION = "It supports managing iptables which is working as layer 3-4 firewall"
SECTION = "webos/apps"
AUTHOR = "cuong.mai"
LICENSE = "CLOSED"

DEPENDS = "iptables"
RDEPENDS:${PN} = "iptables"
PR = "r1"

inherit pkgconfig cmake systemd

#SRC_URI = "${PAP_GENIVI_GIT_REPO_COMPLETE}"
SRC_URI = "https://github.com/TuyPhong212/ufw.git \
	file://firewall-manager \
"

#SRCREV = "${AUTOREV}"
#SRCREV = "e6fbf61f31e1d986a280c2ba9d557b26fc2f8462"
S = "${WORKDIR}/git"

#PACKAGECONFIG = "xtables"
SYSTEMD_SERVICE = "firewall-manager.service"
SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_AUTO_DISABLE = "disable"

#addtask gerrit before do_patch after do_unpack
#do_gerrit() {
#    cd ${S}
#}

pkg_postinst:${PN}() {
    # If systemd enabled, enable the init script
    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
        if [ -n "$D" ]; then
            OPTS="--root=$D"
        fi
        systemctl $OPTS ${SYSTEMD_AUTO_ENABLE} ${SYSTEMD_SERVICE}
#        systemctl $OPTS ${SYSTEMD_AUTO_DISABLE} ${SYSTEMD_SERVICE}
    fi
}

FILES:${PN} = "${sysconfdir} ${systemd_unitdir}"
FILES:${PN} += "/usr/bin"

#INSANE_SKIP:${PN} += "already-stripped"

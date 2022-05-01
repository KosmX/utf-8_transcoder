# This is an example PKGBUILD file. Use this as a start to creating your own,
# and remove these comments. For more information, see 'man PKGBUILD'.
# NOTE: Please fill out the license field for your package! If it is unknown,
# then please put 'unknown'.

# The following guidelines are specific to BZR, GIT, HG and SVN packages.
# Other VCS sources are not natively supported by makepkg yet.

# Maintainer: KosmX kosmx.mc@gmail.com
pkgname=utf8-converter # '-bzr', '-git', '-hg' or '-svn'
pkgver=rr2.8e9b0d4r.r
pkgrel=1
pkgdesc="Stuff to convert Windows local coded junk to UTF-8"
arch=('x86_64')
url="https://github.com/KosmX/utf-8_transcoder"
license=('MIT')
groups=()
depends=('jdk-openjdk')
makedepends=() # 'bzr', 'git', 'mercurial' or 'subversion'
provides=("${pkgname}")
conflicts=()
replaces=()
backup=()
options=()
install=
source=("${pkgname}::git+https://github.com/KosmX/utf-8_transcoder")
noextract=()
md5sums=('SKIP')

# Please refer to the 'USING VCS SOURCES' section of the PKGBUILD man page for
# a description of each element in the source array.

pkgver() {
	cd "$srcdir/${pkgname%-VCS}"

# The examples below are not absolute and need to be adapted to each repo. The
# primary goal is to generate version numbers that will increase according to
# pacman's version comparisons with later commits to the repo. The format
# VERSION='VER_NUM.rREV_NUM.HASH', or a relevant subset in case VER_NUM or HASH
# are not available, is recommended.

# Bazaar
	printf "r%s" "$(bzr revno)"

# Git, tags available
	printf "%s" "$(git describe --long | sed 's/\([^-]*-\)g/r\1/;s/-/./g')"

# Git, no tags available
	printf "r%s.%s" "$(git rev-list --count HEAD)" "$(git rev-parse --short HEAD)"

# Mercurial
	printf "r%s.%s" "$(hg identify -n)" "$(hg identify -i)"

# Subversion
	printf "r%s" "$(svnversion | tr -d 'A-z')"
}

#prepare() {
#	cd "$srcdir/${pkgname%-VCS}"
#	patch -p1 -i "$srcdir/${pkgname%-VCS}.patch"
#}

build() {
	cd "${pkgname}"
	chmod +x gradlew
	./gradlew build
	
	#./configure --prefix=/usr
	#make
}


package() {
	cd "${pkgname}"
	#make DESTDIR="$pkgdir/" install
	mkdir -p "${pkgdir}/usr/bin/"
	cp transcode "${pkgdir}/usr/bin/"
	cp build/libs/*.jar "${pkgdir}/usr/bin/transcoder.jar"
	chmod +x "${pkgdir}/usr/bin/transcode"
}

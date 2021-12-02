# RoboCup

Para utilizar el rcssserver y rcssmonitor hará falta instalar lo siguiente:

- g++
- make
- boost
- bison
- flex
- qt

```
sudo -i
sudo dpkg --configure -a
sudo apt-get update -y
```

#### g++

```
sudo apt install build-essential
```

#### make

```
sudo apt-get install -y make
```

#### boost

```
sudo apt-get install libboost-all-dev
```

#### bison

```
sudo apt-get install bison
```

#### flex

```
sudo apt-get install flex
```

#### qt5

```
sudo apt-get install qtcreator
sudo apt-get install qt5-default
```

Es posible que al instalar qt5 en Ubuntu21 aparezca el siguiente error:

"E: Package 'qt5-default' has no installation candidate"

Esto se puede solucionar con el siguiente código:

```
sudo apt-get install equivs

cd ~/Downloads
cat <<EOF > qt5-default-control
Package: qt5-default
Source: qtbase-opensource-src
Version: 5.99.99+fake-13ubuntu37
Architecture: all
Depends: qtbase5-dev, qtchooser
Suggests: qt5-qmake, qtbase5-dev-tools
Conflicts: qt4-default
Section: libdevel
Priority: optional
Homepage: http://qt-project.org/
Description: Qt 5 development defaults fake package
EOF

equivs-build qt5-default-control
sudo apt-get install ./qt5-default_5.99.99+fake-13ubuntu37_all.deb
```

## Instalando rcssserver

Descargamos el archivo rcssserver-16.0.1.tar.gz de https://github.com/rcsoccersim/rcssserver/releases

Accedemos desde el terminal. Ejemplo:

```
cd '/home/jorge/rcssserver-16.0.1'
```

Ejecutamos lo siguiente:

```
./configure
make
make install
```

## Instalando rcssmonitor

Descargamos el archivo rcssmonitor-16.0.0.tar.gz de https://github.com/rcsoccersim/rcssmonitor/releases

```
cd '/home/jorge/rcssmonitor-16.0.0'
```

```
./configure
make
make install
```

Para lanzar la simulación, acceder a /usr/local/bin y ejecutar rcsoccersim

```
cd '/usr/local/bin'
./rcsoccersim
```

![image](https://user-images.githubusercontent.com/43110966/144472020-b3e47e28-6ee2-469c-9a1e-eb6952f8ba39.png)

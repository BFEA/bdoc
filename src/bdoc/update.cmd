@ECHO ON
:begin
cd %~dp0
mkdir tmp
cd tmp
::wget http://192.168.10.3/bdoc/update.cmd
wget http://192.168.10.3/dist/bdoc/BDOC.jar
::wget http://192.168.10.3/dist/index.html
taskkill /im javaw.exe /T
cd ../
del BDOC.jar
cd tmp
copy "BDOC.jar" "../"
cd ../
rmdir /S /Q tmp
start BDOC.jar
exit
@ECHO OFF

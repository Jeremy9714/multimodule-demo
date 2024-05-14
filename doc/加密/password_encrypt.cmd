@echo off

set INPUT=%1
set PASSWORD=MyRandomPwd
echo password is %INPUT%

java -cp lib\jasypt-1.9.2.jar org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI input="%INPUT%" password=%PASSWORD% algorithm=PBEWithMD5AndDES

pause

@echo off
rd .\target /s /q
mkdir target
javac -classpath "lib\gson-2.8.9.jar;" -d .\target .\src\main\java\com\melissadata\property\*.java .\src\main\java\com\melissadata\property\model\*.java .\src\main\java\com\melissadata\property\view\*.java
xcopy src\main\resources\ target\ /s /q
xcopy lib\ target\lib\ /s /q
@echo off
javac -classpath ".\melissadata\property\org.apache.sling.commons.json-2.0.20.jar;" .\melissadata\property\*.java .\melissadata\property\view\*.java ./melissadata\property\model\*.java
java -classpath ".\melissadata\property\org.apache.sling.commons.json-2.0.20.jar;"; melissadata.property.Main melissadata.property.view.PropertyController melissadata.property.view.PropertyTransactionController melissadata.property.view.RootLayoutController
del .\melissadata\property\*.class 
del .\melissadata\property\view\*.class 
del .\melissadata\property\model\*.class
@echo off
cd target
java -classpath "lib\gson-2.8.9.jar;" com.melissadata.property.Main com.melissadata.property.view.PropertyController com.melissadata.property.view.RootLayoutController com.melissadata.property.model.PropertyOptions com.melissadata.property.model.PropertyTransaction
cd ..
App42-Push-Ane-Source
=====================
 # About Application

1. This application shows how can you build customized ANE(Android Native Extension) for your  Flash Air App using App42 PushNotification.
        
1. You can find complete Flash Android Push Notification sample [here] (https://github.com/VishnuGShephertz/App42-Push-Notification-on-Flash-Android/archive/master.zip) and import it in your FlashBuiler.

2. If you want to customize Push Notification accordingly,you have to build new ANE file using source code we have shared,using follwing steps : 

```
A. Download Complete Sample including build folder.
B. import Ane Source library project in your eclipse.
C. Customize library project accordingly ,build it and export jar in ./build / ane / Android-ARM name as app42flashPush.jar.
D. Now open command prompt and navigate the sample project folder (e.g App42-Push-Ane-Source).
E. Run command  : "<Your Adt path of flash builder>" -package -target ane app42Push.ane build/ane/extension.xml -swc ./build/ane/*.swc -platform Android-ARM -C ./build/ane/Android-ARM .
F . Adt Path e.g : "C:\Program Files\Adobe\Adobe Flash Builder 4.7 (64 Bit)\sdks\4.6.0\bin\adt"
```  

        

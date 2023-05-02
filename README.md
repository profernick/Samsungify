# Samsungify
LSPosed / EdXposed module to use Samsung apps on custom roms and non-Samsung devices
 
[Credits to Pixelify](https://github.com/BaltiApps/Pixelify-Google-Photos.git)  

### Steps to use:
1. Install Magisk, [LSPosed](https://github.com/LSPosed/LSPosed) Or [EdXposed](https://github.com/ElderDrivers/EdXposed).  
2. Install the apk of this app on the release section 
3. Open LSPosed / EdXposed app and enable the module. For LSPosed, select all your Samsung apps.
4. Reboot. Enjoy. (If needed, you might need to clear data of selected apps, and add Samsung systems apps)

### How does this module work?
It simply hooks on to `hasSystemFeature()` method under `android.app.ApplicationPackageManager` class. 
Then when the app checks the relevant features which are expected only on Galaxy devices, the module passes `true`. 
This module can also spoof some of the `build.prop` information like `BRAND`, `MANUFACTURER`, `MODEL`, `FINGERPRINT` of some Galaxy devices.  

### Disclaimer!!
The user takes sole responsibility for any damage that might arise due to use of this module.  
This includes physical damage (to device), injury, data loss, and also legal matters.  
This project was made as a learning initiative and the developer cannot be held liable in any way for the use of it.

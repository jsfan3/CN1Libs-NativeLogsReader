# CN1Lib - Native Logs Reader

Codename One Library (by Francesco Galgani) to easily get the native logs of Android and iOS. It's useful for debugging purposes when we test an app on real devices not connected to Android Studio or XCode.

## Installation

Follow the standard way to install a CN1Lib from Codename One Settings: https://shannah.github.io/codenameone-maven-manual/#managing-addons-in-control-center

## Warning for Android

After a factory reset of my Android 7 device, this library stopped to work. The solution is to enable the developer tools in the Android settings and then the USB debug option: after that, this library started again to work properly, without the need of USB connection (that's why I created it). I'm not sure if it's a general (odd) rule related to logcat or a problem of my device.

## Warning for iOS

I noticed that this CN1Lib can only intercept native logs when the app is launched by tapping it directly on the device. When launched via Xcode, the logs are not intercepted but are shown in the Xcode console instead.

## Usage

1. In the init() of the main class, invoke `NativeLogs.initNativeLogs();`
2. After that, in any point of the app, get the native logs with `String logs = NativeLogs.getNativeLogs();`
3. Use `NativeLogs.clearNativeLogs();` to clear the logs

## Example of usage

```
import net.informaticalibera.cn1.nativelogreader.NativeLogs;

public class MyApp extends Lifecycle {
    
    @Override
    public void init(Object context) {
        super.init(context);
        NativeLogs.initNativeLogs();
    }
    
    @Override
    public void runApp() {
        Form hi = new Form ("Native Logs Reader", BoxLayout.y());
        TextArea textArea = new TextArea(NativeLogs.getNativeLogs());
        Button clearPrintLog = new Button("Clear logs");
        hi.addAll(textArea, clearPrintLog);
        hi.show();
        
        boolean[] isCleared = {false};
        clearPrintLog.addActionListener(l -> {
            if (!isCleared[0]) {
                NativeLogs.clearNativeLogs();
                textArea.setText("Logs cleared");
                clearPrintLog.setText("Print logs");
            } else {
                textArea.setText(NativeLogs.getNativeLogs());
                clearPrintLog.setText("Clear logs");
            }
            isCleared[0] = !isCleared[0];
            hi.revalidateLater();
        });
    }

}
```

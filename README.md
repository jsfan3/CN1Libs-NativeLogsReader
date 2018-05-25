# CN1Lib - Native Logs Reader

Codename One Library (by Francesco Galgani) to get easily the native logs of Android and iOS.

## Usage

1. In the init() of the main class, invoke `NativeLogs.initNativeLogs();`
2. After that, in any point of the app, get the native logs with `String logs = NativeLogs.getNativeLogs();`

## Example of usage

```
package ...

import ...
import net.informaticalibera.cn1.nativelogreader.NativeLogs;

public class MyApplication {

    private Form current;
    private Resources theme;

    public void init(Object context) {
        NativeLogs.initNativeLogs();
        
        // use two network threads instead of one
        updateNetworkThreadCount(2);

        theme = UIManager.initFirstTheme("/theme");

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature
        Log.bindCrashProtection(true);

        addNetworkErrorListener(err -> {
            // prevent the event from propagating
            err.consume();
            if (err.getError() != null) {
                Log.e(err.getError());
            }
            Log.sendLogAsync();
            Dialog.show("Connection Error", "There was a networking error in the connection to " + err.getConnectionRequest().getUrl(), "OK", null);
        });
    }

    public void start() {
        if (current != null) {
            current.show();
            return;
        }
        
        Form hi = new Form ("Native Logs Reader", BoxLayout.y());
        String logs = NativeLogs.getNativeLogs();
        TextArea textArea = new TextArea(logs);
        hi.add(textArea);
        hi.show();
        
        

    }

    public void stop() {
        current = getCurrentForm();
        if (current instanceof Dialog) {
            ((Dialog) current).dispose();
            current = getCurrentForm();
        }
    }

    public void destroy() {
    }

}
```
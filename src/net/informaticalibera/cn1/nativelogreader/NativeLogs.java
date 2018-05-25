/**
 * Native Logs Reader
 * Written in 2018 by Francesco Galgani, https://www.informatica-libera.net/
 *
 * To the extent possible under law, the author(s) have dedicated all copyright
 * and related and neighboring rights to this software to the public domain worldwide.
 * This software is distributed without any warranty.
 *
 * You should have received a copy of the CC0 Public Domain Dedication along
 * with this software. If not, see
 * <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package net.informaticalibera.cn1.nativelogreader;

import com.codename1.io.Log;
import com.codename1.system.NativeLookup;
import com.codename1.ui.Display;

/**
 * <p>
 * Native Logs Reader</p>
 * <p>
 * Usage: first of all, remember to invoke the
 * {@link #initNativeLogs() initNativeLogs()} method in the init(). After that,
 * {@link #getNativeLogs() getNativeLogs()} can be used in any point of the app
 * to retrive the native logs of Android (provided by Logcat) and the native
 * logs of iOS (written in the stderr and stdout).</p>
 */
public class NativeLogs {

    private static NativeLogsReader nativeLogsReader = null;
    private static boolean isInitialized = false;

    private NativeLogs() {

    }

    /**
     * Call this method in the init() to initialize the Native Logs Reader
     */
    public static void initNativeLogs() {
        if (!Display.getInstance().isSimulator()) {
            nativeLogsReader = NativeLookup.create(NativeLogsReader.class);
            if (nativeLogsReader != null && nativeLogsReader.isSupported()) {
                nativeLogsReader.clearAndRestartLog();
                Log.p("Native Logs Reader initialized correctly");
            } else {
                Log.p("Native Logs Reader cannot be executed in the current platform");
            }
            isInitialized = true;
        } else {
            Log.p("Native Logs Reader is disabled in the Simulator");
        }
    }

    /**
     * Gets the native log of iOS and Android, remember to invoke
     * {@link #initNativeLogs() initNativeLogs()} in the init() of the main
     * class.
     *
     * @return the native log, or a warning if the native log isn't available
     */
    public static String getNativeLogs() {
        if (Display.getInstance().isSimulator()) {
            return "No native log in the Simulator...";
        }
        if (isInitialized) {
            String log = "";
            if (nativeLogsReader != null && nativeLogsReader.isSupported()) {
                String reader = nativeLogsReader.readLog();
                if (reader != null) {
                    log = reader;
                }
            } else {
                log = "Native log is not available in the current platform";
            }
            return log;
        } else {
            Log.p("getNativeLogs() cannot be executed without calling initNativeLogs() before");
            return "getNativeLogs() cannot be executed without calling initNativeLogs() before";
        }
    }
}

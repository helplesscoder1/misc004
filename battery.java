import android.os.Build;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.net.ssl.HttpsURLConnection;


    public static void printBatteryLife() {
        try {
            Log.d("Battery", "Battery: " + runShellCommand("cat /sys/class/power_supply/battery/capacity") + "%");
        } catch (Exception e) {
            Log.e("Battery", "Error reading battery", e);
        }
    }

    public static void printSerialNumber() {
        try {
            Log.d("Serial", "Serial: " + runShellCommand("getprop ro.boot.serialno"));
        } catch (Exception e) {
            Log.e("Serial", "Error reading serial number", e);
        }
    }

    public static void printImei() {
        try {
            String output = runShellCommand("service call iphonesubinfo 4 i32 0 s16 com.android.shell s16 com.android.shell");
            StringBuilder imei = new StringBuilder();
            for (String l : output.split("\n")) {
                int start = l.indexOf("'");
                int end = l.lastIndexOf("'");
                if (start != -1 && end != -1 && start != end) {
                    for (char c : l.substring(start + 1, end).toCharArray()) {
                        if (Character.isDigit(c)) imei.append(c);
                    }
                }
            }
            Log.d("IMEI", "IMEI: " + imei.substring(0, 15));
        } catch (Exception e) {
            Log.e("IMEI", "Error reading IMEI", e);
        }
    }
}



On a new Android version you'd need to either:
•  Brute-force codes 1–10 via ADB like we did (takes 2 minutes)
•  Check the AOSP source for IPhoneSubInfo.aidl for that version to find the right code

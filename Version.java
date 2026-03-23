import android.os.Build;

import java.io.OutputStream;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.net.ssl.HttpsURLConnection;

public class NetworkFunctions {

    private static final String SERVER_URL = "https://172.20.1.21:5000/message";

    public static void postAndroidVersion() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            HttpsURLConnection conn = null;
            try {
                URL url = new URL(SERVER_URL);
                conn = (HttpsURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "text/plain");
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);

                String androidVersion = Build.VERSION.RELEASE;
                byte[] body = androidVersion.getBytes("UTF-8");
                conn.setFixedLengthStreamingMode(body.length);

                try (OutputStream os = conn.getOutputStream()) {
                    os.write(body);
                }

                int responseCode = conn.getResponseCode();
                System.out.println("Response code: " + responseCode);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (conn != null) conn.disconnect();
            }
        });
    }
}

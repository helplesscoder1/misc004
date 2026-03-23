import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.X509Certificate;
import javax.net.ssl.*;

public class GetContent {

    public static String fetch(String urlString) {
        try {
            // Trust all certs (dev only)
            TrustManager[] trustAll = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() { return null; }
                        public void checkClientTrusted(X509Certificate[] c, String a) {}
                        public void checkServerTrusted(X509Certificate[] c, String a) {}
                    }
            };
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAll, new java.security.SecureRandom());

            HttpsURLConnection connection = (HttpsURLConnection) new URL(urlString).openConnection();
            connection.setSSLSocketFactory(sc.getSocketFactory());
            connection.setHostnameVerifier((hostname, session) -> true);
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            if (connection.getResponseCode() != HttpsURLConnection.HTTP_OK) {
                return "Error: No content received";
            }

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            StringBuilder html = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                html.append(line).append("\n");
            }
            reader.close();
            connection.disconnect();

            String result = html.toString().trim();
            return result.isEmpty() ? "Error: No content received" : result;

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}




import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class GetContent {

    public static String fetch(String urlString) {
        try {
            HttpsURLConnection connection = (HttpsURLConnection) new URL(urlString).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            if (connection.getResponseCode() != HttpsURLConnection.HTTP_OK) {
                return "Error: No content received";
            }

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            StringBuilder html = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                html.append(line).append("\n");
            }
            reader.close();
            connection.disconnect();

            String result = html.toString().trim();
            return result.isEmpty() ? "Error: No content received" : result;

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}

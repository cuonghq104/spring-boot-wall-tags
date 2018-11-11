package ptit.cuonghq.walltag.utils;

import com.google.gson.JsonObject;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class CommonServices {

    public static JSONObject requestGET(String targetUrl, JsonObject requestBody) throws IOException {
        HttpURLConnection connection = null;
        String inputLine;
        StringBuffer response = new StringBuffer();

        try {
            URL url = new URL(targetUrl);
            // Make connection
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            if (connection.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                JSONObject obj = new JSONObject(response.toString());
                return obj;
            } else {
                System.out.println("Can't get data");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}

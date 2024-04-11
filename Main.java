import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        String apiKey = "TWzWjz2zSSGmx8eAZ1mlYZPzxUd8X919";
        String symbol = "AAPL"; // Example symbol
        String date = "2023-04-10";
        try {
            // URL url = new URL("https://api.polygon.io/v1/open-close/" + symbol + "/2023-01-17?unadjusted=true&apiKey=" + apiKey);
            // "https://api.polygon.io/v2/aggs/ticker/AA/range/1/day/2023-04-10/2023-04-10?adjusted=true&sort=asc&limit=120&apiKey=TWzWjz2zSSGmx8eAZ1mlYZPzxUd8X919"
            URL url = new URL("https://api.polygon.io/v2/aggs/ticker/"+ symbol +"/range/1/day/"+date+"/"+date+"?adjusted=true&sort=asc&limit=120&apiKey=" + apiKey);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                System.out.println("Response from Polygon.io API:");
                System.out.println(response.toString());
            } else {
                System.out.println("Failed to fetch data. Response code: " + responseCode);
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

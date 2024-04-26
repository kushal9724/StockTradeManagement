package com.example.TradeManagement.Model.Service;

import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;
import com.example.TradeManagement.Model.Stock;
import com.example.TradeManagement.Model.StockApiResponse;
import com.example.TradeManagement.Model.StockData;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.text.DecimalFormat;

@Service
public class StockService {

    // private static final String apiKey = "MPBS7SE4HCCC7OCP";
    private static final String apiKey = "8RI3IKQID2QHVQYR";
    private static final String API_URL = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=IBM&interval=5min&" + apiKey;

    private final RestTemplate restTemplate;

    public StockService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // public List<Stock> fetchStockData() {
    //     // Make API call to fetch stock data
    //     StockApiResponse response = restTemplate.getForObject(API_URL, StockApiResponse.class);
        
    //     // Extract stock data from the API response and construct Stock objects
    //     List<Stock> stocks = new ArrayList<>();
    //     for (Stock stockData : response.getStocks()) {
    //         Stock stock = new Stock(stockData.getSymbol(), stockData.getName(), stockData.getPrice(), stockData.getQuantity(), stockData.getDate(), stockData.getGain(), stockData.getValue());
    //         stocks.add(stock);
    //     }
        
    //     return stocks;
    // }

    public Stock fetchStockData(String tickerSymbol, String date, int quantity) {
        System.out.println(12);
        System.out.println(date);
        // Construct the API URL with the provided ticker symbol and API key
        // String apiUrl = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" + 
        //         tickerSymbol + "&apikey=" + apiKey;
        String apiUrl = "https://api.polygon.io/v2/aggs/ticker/"+tickerSymbol+"/range/1/day/"+date+"/"+date+"?adjusted=true&sort=desc&limit=120&apiKey=TWzWjz2zSSGmx8eAZ1mlYZPzxUd8X919";

        // Make API call to fetch stock data
        StockApiResponse response = restTemplate.getForObject(apiUrl, StockApiResponse.class);
        
        // Extract the necessary information from the API response
        // (e.g., stock price) and calculate the total price
        double stockPrice = calculateStockPrice(response);
        
        double c_price = getCurrentPrice(tickerSymbol);

        // double totalPrice = stockPrice * quantity;
        double gain = c_price - stockPrice;

        DecimalFormat df = new DecimalFormat("#.###");
        String formattedValue = df.format(gain);
        double formattedValueAsDouble = Double.parseDouble(formattedValue);

        double value = quantity * stockPrice;
        System.out.println( "q "  + quantity);
        System.out.println( "p "  + stockPrice);
        // Create a new Stock object with the calculated total price
        Stock stock = new Stock(tickerSymbol, "", stockPrice, quantity, date, formattedValueAsDouble, value);
        
        return stock;
    }

    private double calculateStockPrice(StockApiResponse response) {


        List<StockData> stocks = response.getResults();
    
        for (StockData stock : stocks) {
            return stock.getL();
        }

        return 0;
    }


    public double getCurrentPrice(String tickerSymbol) {
        LocalDate today = LocalDate.now();

        // Subtract one day to get yesterday's date
        LocalDate yesterday = today.minusDays(1);

        // Format yesterday's date as a string
        String date = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        String apiUrl = "https://api.polygon.io/v2/aggs/ticker/"+tickerSymbol+"/range/1/day/"+date+"/"+date+"?adjusted=true&sort=desc&limit=120&apiKey=TWzWjz2zSSGmx8eAZ1mlYZPzxUd8X919";
        
        // Make API call to fetch stock data
        StockApiResponse response = restTemplate.getForObject(apiUrl, StockApiResponse.class);
        
        double stockPrice = calculateStockPrice(response);
        
        return stockPrice;
    }

    public List<Double> fetchStockDataForVisualization(String tickerSymbol) {
        // Make API call to fetch stock data for visualization

        LocalDate today = LocalDate.now();

        // Subtract one day to get yesterday's date
        LocalDate yesterday = today.minusDays(1);

        // Format yesterday's date as a string
        String date = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String date2 = "2024-01-01";

        String apiUrl = "https://api.polygon.io/v2/aggs/ticker/"+tickerSymbol+"/range/1/day/"+date2+"/"+date+"?adjusted=true&sort=asc&limit=120&apiKey=TWzWjz2zSSGmx8eAZ1mlYZPzxUd8X919";

        // Example: StockApiResponse response = restTemplate.getForObject(API_URL, StockApiResponse.class);
        StockApiResponse response = restTemplate.getForObject(apiUrl, StockApiResponse.class);
        
        // Process the API response and extract the necessary data
        // Example: List<StockData> stockData = processApiResponse(response);

        List<StockData> stocks = response.getResults();
        List<Double> stocksCp = new ArrayList<>();
        // Return the extracted stock data
        for(StockData stock : stocks) {
            stocksCp.add(stock.getC());
        }
        System.out.println(stocksCp);
        return stocksCp;

    
}
    
}


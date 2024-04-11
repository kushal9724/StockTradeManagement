package com.example.TradeManagement.Model.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TradeManagement.Model.Stock;
import com.example.TradeManagement.Model.Trader;
import com.example.TradeManagement.Model.TraderRepository;
import java.text.DecimalFormat;

@Service
public class TraderService {

    @Autowired
    private TraderRepository traderRepository;

    public Trader registerTrader(Trader trader) {
        // Additional validation, encryption of password, etc.
        return traderRepository.save(trader);
    }

    public boolean authenticate(Trader trader) {
        // Extract the username and password from the input Trader object
        String username = trader.getUsername();
        String password = trader.getPassword();

        // Find the user by username in the database
        Trader backend = traderRepository.findByUsername(username);
        
        System.out.println(backend);
        if (backend == null) {
            return false;
        }

        // Extract the stored password from the database
        String storedPassword = backend.getPassword();

        // Compare the stored password with the provided password
        if (password.equals(storedPassword)) {
            return true;
        } else {
            return false;
        }
    }

    public Trader getTraderByUsername(String username){

        Trader trader = traderRepository.findByUsername(username);

        return trader;
    }

    public List<Stock> getTraderStocks(Trader trader) {
        List<Stock> stocks = trader.getPortfolio();

        return stocks;

    }

    public Trader invest(String username, Stock stockData) {
        // Retrieve the trader object using the provided username
        Trader trader = traderRepository.findByUsername(username);

        // Get the portfolio list from the retrieved trader object
        List<Stock> portfolio = trader.getPortfolio();
        
        // Add the new stock to the portfolio list
        portfolio.add(stockData);

        // Update the trader object with the updated portfolio
        trader.setPortfolio(portfolio);

        // Save the updated trader object back to the database
        trader = traderRepository.save(trader);

        return trader;
    }

    public void updateTrader(Trader trader) {
        // Save the updated trader object to the database
        traderRepository.save(trader);
    }

    public void adjustStockQuantity(String username, int stockIndex, int newQuantity) {
        // Retrieve the trader by username
        Trader trader = traderRepository.findByUsername(username);
        
        // Retrieve the portfolio list from the trader
        List<Stock> portfolio = trader.getPortfolio();
        
        // Ensure the stockIndex is within the bounds of the portfolio list
        if (stockIndex >= 0 && stockIndex < portfolio.size()) {
            // Retrieve the stock object at the specified index
            Stock stock = portfolio.get(stockIndex);
            
            // Update the quantity of the stock
            stock.setQuantity(newQuantity);
            double value = newQuantity * stock.getPrice();

            DecimalFormat df = new DecimalFormat("#.###");
            String formattedValue = df.format(value);
            double formattedValueAsDouble = Double.parseDouble(formattedValue);
            stock.setValue(formattedValueAsDouble);

            // Save the updated trader object to the database
            traderRepository.save(trader);
        } else {
            // Handle index out of bounds error or other appropriate error handling
            throw new IllegalArgumentException("Invalid stock index");
        }
    }
    // Other methods for updating portfolio, buying/selling stocks, etc.
}

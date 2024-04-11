package com.example.TradeManagement.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.TradeManagement.Model.Stock;
import com.example.TradeManagement.Model.Trader;
import com.example.TradeManagement.Model.Service.StockService;
import com.example.TradeManagement.Model.Service.TraderService;


import java.util.List;
import org.springframework.ui.Model;
import java.text.DecimalFormat;
import java.lang.Thread;

@Controller
public class AuthController {
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }


    @Autowired
    private TraderService traderService;
    @Autowired
    private StockService stockService;

    @PostMapping("/register")
    public String registerTrader(@RequestParam String email, 
                                 @RequestParam String username,
                                 @RequestParam String password,
                                 Model model) {
        try {
            // Create a Trader object with the received parameters
            Trader trader = new Trader(email, username, password);
            
            // Call the service method to register the trader
            traderService.registerTrader(trader);

            // Redirect to a success page or another appropriate page
            return "redirect:/login"; // Redirect to the login page after successful registration
        } catch (Exception e) {
            // If an error occurs, add error message to model
            model.addAttribute("error", "An error occurred during registration: " + e.getMessage());
            
            // Return to the registration page with error message
            return "register";
        }
    }

    @PostMapping("/login")
    public String loginTrader(@RequestParam String username, 
                            @RequestParam String password,
                            Model model) {
        try {
            // Create a Trader object with the received parameters
            Trader trader = new Trader(username, password);
            
            // Call the service method to authenticate the trader
            boolean authenticated = traderService.authenticate(trader);
            
            if (authenticated) {
                // Redirect to the home page specific to the logged-in user
                System.out.println(authenticated);
                model.addAttribute(username, trader);
                return "redirect:/" + username + "/home";
            } else {
                // Authentication failed, return to the login page with an error message
                model.addAttribute("error", "Invalid username or password");
                return "login"; // Assuming you have a login page named "login.html"
            }
        } catch (Exception e) {
            // Handle exceptions or errors
            model.addAttribute("error", "An error occurred during login");
            return "login"; // Return to the login page with an error message
        }
    }

    @GetMapping("/{username}/home")
    public String home(@PathVariable String username, Model model) {
        // Add the username to the model
        model.addAttribute("username", username);
        Trader trader = traderService.getTraderByUsername(username);
        List<Stock> portfolio = trader.getPortfolio();
        
        // Iterate through each stock in the portfolio
        for (Stock stock : portfolio) {
            // Retrieve the purchase price of the stock from the database based on the date of purchase
            double purchasePrice = stock.getPrice();

            // Make an API request to retrieve the current price of the stock
            double currentPrice = stockService.getCurrentPrice(stock.getSymbol());

            // Calculate the gain for the stock
            double gain = currentPrice - purchasePrice;
            System.out.println("c price" + currentPrice);
            System.out.println("p price" + purchasePrice);
            // Set the gain for the stock
            DecimalFormat df = new DecimalFormat("#.##");
            String formattedValue = df.format(gain);
            double formattedGain = Double.parseDouble(formattedValue);
            stock.setGain(formattedGain);

            try {
                Thread.sleep(2000); // Sleep for 1 second (1000 milliseconds)
            } catch (InterruptedException e) {
                // Handle InterruptedException
                System.out.println("Could not stop");
            }
        }


        model.addAttribute("portfolio", portfolio);

        // Return the name of the Thymeleaf template to render
        return "home";
    }

    @GetMapping("/{username}/invest")
    public String investForm(@PathVariable String username, Model model) {

        model.addAttribute( "username" ,username);

        return "invest";
    }


    @PostMapping("/{username}/invest")
    public String invest(@PathVariable String username,
                         @RequestParam String ticker,
                         @RequestParam String date,
                         @RequestParam int quantity,
                         Model model) {
        // Get the username of the logged-in user
        Stock stockData = stockService.fetchStockData(ticker, date, quantity);
        
        traderService.invest(username, stockData);
    
        // Return a success message to the user
        model.addAttribute("message", "Investment successful");
        
        return "redirect:/" + username + "/home";
    }

    @PostMapping("/{username}/deleteStock")
    public String deleteStock(@PathVariable String username, @RequestParam("stockIndex") int stockIndex) {
        // Get the trader by username
        Trader trader = traderService.getTraderByUsername(username);
        
        // Remove the stock at the specified index from the portfolio list
        trader.getPortfolio().remove(stockIndex);
        
        // Update the trader's portfolio in the database
        traderService.updateTrader(trader);
        
        // Redirect to the home page
        return "redirect:/" + username + "/home";
    }

    @PostMapping("/{username}/adjustQuantity")
    public String updateQuantity(@PathVariable String username, @RequestParam("stockIndex") int stockIndex, @RequestParam("quantity") int quantity) {

        traderService.adjustStockQuantity(username, stockIndex, quantity);

        return "redirect:/" + username + "/home";
    }
}

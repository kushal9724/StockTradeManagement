package com.example.TradeManagement.Controller;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.TradeManagement.Model.Stock;
import com.example.TradeManagement.Model.Service.StockService;
import com.example.TradeManagement.Model.Service.TraderService;

@RestController
@RequestMapping("/traders")
public class TraderController {

    @Autowired
    private TraderService traderService;
    private StockService stockService;

    @GetMapping("/{username}/home")
    public String home(@PathVariable String username, Model model) {
        // Add the username to the model
        System.out.println(username);
        model.addAttribute("username", username);
        System.out.println(username);
        // Return the name of the Thymeleaf template to render
        return "home";
    }

    
    @GetMapping("/{username}/invest")
    public String investForm(@PathVariable String username, Model model) {

        model.addAttribute( "username" ,username);

        return "invest";
    }


    // Other controller methods for handling trader-related operations
}

package com.example.TradeManagement.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;

import java.util.List;

@Document(collection = "traders")
public class Trader {
    @Id
    private String id;
    private String username;
    private String password;
    private String email;
    private List<Stock> portfolio;

    // Default constructor
    public Trader() {
        this.portfolio = new ArrayList<>();
    }

    public Trader(String email, String username, String password){
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public Trader(String username, String password){
        this.username = username;
        this.password = password;
    }

    // Constructors, getters, and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Stock> getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(List<Stock> portfolio) {
        this.portfolio = portfolio;
    }

    public void addToPortfolio(Stock stock) {
        if (portfolio == null) {
            portfolio = new ArrayList<>();
        }
        portfolio.add(stock);
    }

    @Override
    public String toString() {
        return "Trader{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email=" + email +
                ", password='" + password + '\'' +
                '}';
    }
}

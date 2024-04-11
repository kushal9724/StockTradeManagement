package com.example.TradeManagement.Model;
//import org.springframework.data.annotation.Id;
// import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Stock {
    private String symbol;
    private String name;
    private double price;
    private int quantity;
    private String date;
    private double gain;
    private double value;

    // Default constructor (required by Jackson)
    public Stock() {
    }

    // Constructor with arguments
    public Stock(String symbol, String name, double price, int quantity, String date, double gain, double value) {
        this.symbol = symbol;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.date = date;
        this.gain = gain;
        this.value = value;
    }

    // Getters and setters
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getGain() {
        return gain;
    }

    public void setGain(double gain) {
        this.gain = gain;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    } 
}

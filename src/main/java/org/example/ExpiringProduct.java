package org.example;

public class ExpiringProduct extends Product{
    String expiryDate;

    public ExpiringProduct(String name, float price, int quantity,String expiryDate) {
        super(name, price, quantity);
        this.expiryDate=expiryDate;
    }
}

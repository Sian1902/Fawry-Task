package org.example;

public class ExpiringShippedProduct extends ExpiringProduct implements Shipping{
double weight;

    public ExpiringShippedProduct(String name, float price, int quantity, String expiryDate, double weight) {
        super(name, price, quantity, expiryDate);
        this.weight = weight;

    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public String getName() {
        return name;
    }
}

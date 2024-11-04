package org.example;

public class NonExpiringShippedProduct extends NonExpiringProduct implements Shipping {
double weight;

    public NonExpiringShippedProduct(String name, float price, int quantity, double weight) {
        super(name, price, quantity);
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

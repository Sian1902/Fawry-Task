package org.example;

public abstract class Product {
    String name;
    float Price;
    int quantity;

    public int getQuantity() {
        return quantity;
    }

    public Product(String name, float price, int quantity) {
        this.name = name;
        Price = price;
        this.quantity = quantity;
    }
    public void addItems(int numberOfItems) {
        quantity += numberOfItems;
        System.out.printf("Items added available quantity of %s is %d/n",this.name,this.quantity);
    }
    public void removeItems(int numberOfItems)throws ItemOutOfStockException{
        if(numberOfItems>quantity)throw new ItemOutOfStockException("We don't have the amount you asked for in stock");
        quantity-=numberOfItems;


    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return Price;
    }
}

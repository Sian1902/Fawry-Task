package org.example;

import java.util.HashMap;

public class Customer {
    String name;
    float balance;
    HashMap<String,Integer>cart;

    public Customer(String name, float balance) {
        this.name = name;
        this.balance = balance;
        cart=new HashMap<>();
    }
    public void resetCart(){
        cart.clear();
    }
    public void pay(double amount){
        balance-=amount;
    }
    public void addToCart(String name,Integer count){
        String formattedName=name.toLowerCase();
        if(cart.containsKey(formattedName)){
            cart.put(formattedName, cart.get(formattedName)+count);
        }
        else{
            cart.put(formattedName,count);
        }
    }
    public void removeFromCart(String name,Integer count)throws EmptyCartException{
        String formattedName=name.toLowerCase();
        if(cart.containsKey(formattedName)){
            cart.put(formattedName, cart.get(formattedName)-count);
        }
        else{
            throw new EmptyCartException("This item is already removed from your cart");
        }
    }

    public HashMap<String, Integer> getCart() {
        return cart;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
}

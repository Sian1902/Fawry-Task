package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class Main {
    public static void main(String[] args) {
        HashMap<String,Product>prodsList=new HashMap<String,Product>();
        ExpiringProduct p1= new ExpiringShippedProduct("cheese",10,20,"20-11-2025",200);
        ExpiringProduct p2= new ExpiringShippedProduct("tomato",5,10,"30-11-2024",100);
        ExpiringProduct p3= new ExpiringProduct("movieticket",50,30,"25-11-2024");
        prodsList.put(p1.getName().toLowerCase(),p1);
        prodsList.put(p2.getName().toLowerCase(),p2);
        prodsList.put(p3.getName().toLowerCase(),p3);
        //successful order
        Customer customer= new Customer("Ahmed",1000);
        customer.addToCart("cheese",3);
        customer.addToCart("tomato",5);
        customer.addToCart("movieticket",5);
        testRun(customer,prodsList);
        customer.resetCart();
        //ordering item that doesn't exist in the list
        customer.addToCart("pickles",3);
        testRun(customer,prodsList);
        //ordering non shipable items only
        customer.resetCart();
        customer.addToCart("movieticket",5);
        testRun(customer,prodsList);
        //ordering more items than available
        customer.resetCart();
        customer.addToCart("tomato",10);
        testRun(customer,prodsList);
        //ordering items that exceeds available balance
        customer.resetCart();
        customer.addToCart("movieticket",20);
        testRun(customer,prodsList);

    }

    private static void testRun(Customer customer, HashMap<String, Product> prodsList) {
        try {
            checkout(customer,prodsList);
        }
        catch (ItemOutOfStockException e){
            System.out.println(e.getMessage());
        }
        catch (OutOfBalanceException e){
            System.out.println(e.getMessage());

        }
        catch (EmptyCartException e){
            System.out.println(e.getMessage());
        }
        finally{
            System.out.println("Have a nice day");
        }
    }

    private static void checkout(Customer customer, HashMap<String, Product> prodsList)
    throws ItemOutOfStockException,OutOfBalanceException,EmptyCartException{
     if(customer.getCart().isEmpty())throw new EmptyCartException("Try adding some product to your cart");
     checkAvailability(customer,prodsList);
     //proceed with the checkout
        HashMap<Shipping,Integer>listToBeShipped= new HashMap<Shipping,Integer>();
        ArrayList<CheckoutItem>items=new ArrayList<CheckoutItem>();
        for(Map.Entry<String,Integer>it:customer.getCart().entrySet()){
            Product prod=prodsList.get(it.getKey());
            prod.removeItems(it.getValue());
            items.add(new CheckoutItem(prod.getName(),it.getValue(),prod.getPrice()));
            if(prod instanceof Shipping shippedItem){
                listToBeShipped.put(shippedItem, it.getValue());
            }
        }

        double shippingPrice=ShippingService.getInstance().processShipment(listToBeShipped);
        double subTotal=0;
        System.out.println("--------------------------------");
        System.out.println("** Checkout Receipt **");
        System.out.println("Quantity\tItem Name\tItem Price\tTotal");
        for(CheckoutItem it : items){
            System.out.printf("%d\t\t\t%s\t%.2f\t%.2f\n",it.quantity(),it.name(),it.price(),it.price()*it.quantity());
            subTotal+=it.price()* it.quantity();
        }
        System.out.println("--------------------------------");
        System.out.printf("Subtotal\t%.2f\n",subTotal);
        System.out.printf("Shipping\t%.2f\n",shippingPrice);
        System.out.printf("Total\t%.2f\n",subTotal+shippingPrice);
        customer.pay(subTotal+shippingPrice);
    }

    private static void checkAvailability(Customer customer, HashMap<String, Product> prodsList)
    throws OutOfBalanceException,ItemOutOfStockException{
        double totalPrice=0;
        HashMap<Shipping,Integer>listToBeShipped= new HashMap<Shipping,Integer>();
        for(HashMap.Entry<String,Integer>it:customer.getCart().entrySet()){
            Product prod=prodsList.get(it.getKey());
            if(prod==null)throw new ItemOutOfStockException("The quantity you requested isn't available in stock");
            if(prod.getQuantity()<it.getValue())throw new ItemOutOfStockException("The quantity you requested isn't available in stock");
            totalPrice+=prod.getPrice()*it.getValue();
            if(prod instanceof Shipping shippedItem){
                listToBeShipped.put(shippedItem, it.getValue());
            }
        }
        totalPrice+=ShippingService.getInstance().calculateShippingPrice(listToBeShipped);
        if(totalPrice>customer.getBalance())throw new OutOfBalanceException("Your balance isn't enough");
    }



}
package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShippingService {
    private static ShippingService instance;
    final int pricePerKilo=25;
    private ShippingService(){

    }
    public static ShippingService getInstance(){
        if(instance==null) instance= new ShippingService();
        return instance;
    }
    public double calculateShippingPrice(HashMap<Shipping,Integer> listToBeShipped){
        double totalPrice=0;
        for(Map.Entry<Shipping,Integer>it:listToBeShipped.entrySet()){
            double weightInKilo=it.getKey().getWeight()/1000;
            totalPrice+= it.getValue()*weightInKilo*pricePerKilo;
        }
        return totalPrice;
    }


    public double processShipment(HashMap<Shipping,Integer> listToBeShipped){
        double totalWeight=0;
        if(listToBeShipped.isEmpty())return 0;
        System.out.println("** Shipment Notice **");
        for(Map.Entry<Shipping,Integer>it : listToBeShipped.entrySet() ){
            Shipping item=it.getKey();
            System.out.printf("%d X %s\t%.2f g\n",it.getValue(),item.getName(),item.getWeight()* it.getValue());
            totalWeight+=item.getWeight()* it.getValue();
        }

        System.out.printf("Total package weight %.2f kg\n",totalWeight/1000);
        return calculateShippingPrice(listToBeShipped);
    }
}

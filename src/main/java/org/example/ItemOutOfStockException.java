package org.example;

public class ItemOutOfStockException extends Exception{
    public ItemOutOfStockException(String message) {
        super(message);
    }
}

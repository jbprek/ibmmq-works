package com.foo.jms.tx.simple.warehouse;

public class WarehouseOutOfOrderException extends RuntimeException {
    public WarehouseOutOfOrderException() {
        super("Warehouse out of order, try later!");
    }
}

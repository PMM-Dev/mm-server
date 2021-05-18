package com.kwon770.mm.domain.restaurant;

public enum Price {
    CHEAP("Cheap"),
    EXPENSIVE("Expensive"),
    REASONABLE("Reasonable");

    private String price;

    Price (String price) {
        this.price = price;
    }
}

package com.kwon770.mm.domain.restaurant;

public enum Type {
    KOREAN("Korean"),
    FLOUR("Flour"),
    DESSERT("Dessert"),
    JAPANESE("Japanese"),
    FASTFOOD("Fastfood"),
    WESTERN("Western"),
    ASIAN("Asian"),
    NIGHTFOOD("NightFood");

    private String type;

    Type (String type) {
        this.type = type;
    }
}

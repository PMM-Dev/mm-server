package com.kwon770.mm.domain.restaurant;

public enum Location {
    FRONTGATE("FrontGate"),
    SIDEGATE("SideGate"),
    BACKGATE("BackGate"),
    ARTGATE("ArtGate");

    private String location;

    Location(String location) {
        this.location = location;
    }
}

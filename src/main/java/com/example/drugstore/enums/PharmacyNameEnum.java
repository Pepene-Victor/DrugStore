package com.example.drugstore.enums;

public enum PharmacyNameEnum {
    BEBETEI("BEBETEI"),
    PFARMA("PFARMA"),
    PILULKA("PILULKA"),
    SPRING("SPRING"),
    DAV("DAV"),
    MATTCA("MATTCA"),
    REMEDIUMFARM("REMEDIUMFARM"),
    HELPNET("HELPNET("),
    MINIFARM("MINIFARM"),
    DRMAX("DRMAX"),
    TEI("TEI"),
    CLICKPHARM("CLICKPHARM"),
    DRUGSTORE("DRUGSTORE");

    private final String name;

    PharmacyNameEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

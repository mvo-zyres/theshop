package com.onlineshop.theshop.typesense;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CollectionField {

    public CollectionField() {
        this(null, null, false);
    }

    public CollectionField(String name, String type, boolean facet) {
        this.name = name;
        this.type = type;
        this.facet = facet;
    }

    @JsonProperty("name")
    public String name;

    @JsonProperty("type")
    public String type;

    @JsonProperty("facet")
    public boolean facet;
}


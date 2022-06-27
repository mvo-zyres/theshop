package com.onlineshop.theshop.typesense;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Entry {

    @JsonProperty("document")
    public Document document;

    public Entry(Document document) {
        this.document = document;
    }

    public Entry(){

    }
}

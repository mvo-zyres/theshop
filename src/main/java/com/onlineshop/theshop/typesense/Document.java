package com.onlineshop.theshop.typesense;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Document {
    @JsonProperty("id")
    public String id;

    @JsonProperty("name")
    public String name;

    @JsonProperty("productid")
    public String productid;

    @JsonProperty("temp")
    public Integer temp;

    @JsonProperty("tags")
    public String tags;

    public Document(String id, String name, String productid, Integer temp, String tags) {
        this.id = id;
        this.name = name;
        this.productid = productid;
        this.temp = temp;
        this.tags = tags;
    }

    public Document(){

    }
}

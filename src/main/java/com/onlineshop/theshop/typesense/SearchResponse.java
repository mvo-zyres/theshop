package com.onlineshop.theshop.typesense;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SearchResponse {

    @JsonProperty("found")
    public Integer found;

    @JsonProperty("hits")
    public List<Entry> hits;


    public SearchResponse(Integer found, List<Entry> hits) {
        this.found = found;
        this.hits = hits;
    }

    public SearchResponse(){

    }
}

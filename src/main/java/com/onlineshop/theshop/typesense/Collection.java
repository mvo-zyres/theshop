package com.onlineshop.theshop.typesense;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Collection {

    public Collection() {
        this(null,null);
    }

    public Collection(String name, String defaultSortingField) {
        this.name = name;
        this.fields = new ArrayList<>();
        this.defaultSortingField = defaultSortingField;
    }

    @JsonProperty("name")
    public String name;

    @JsonProperty("fields")
    public List<CollectionField> fields;

    @JsonProperty("default_sorting_field")
    public String defaultSortingField;

    public CollectionField addField(String name, String type) {
        CollectionField field = new CollectionField();
        field.name = name;
        field.type = type;
        fields.add(field);
        return field;
    }

    public CollectionField addField(String name, String type, boolean facet ) {
        CollectionField field = new CollectionField();
        field.name = name;
        field.type = type;
        field.facet = facet;
        fields.add(field);
        return field;
    }
}
package com.healthmonitor.serializers;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class Serializer<T> {

    @JsonProperty("id")
    protected int id;

    public int getId() {
        return id;
    }
    
}

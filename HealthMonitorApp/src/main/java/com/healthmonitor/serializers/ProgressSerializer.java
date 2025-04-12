package com.healthmonitor.serializers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.healthmonitor.pojo.Progress;

public class ProgressSerializer extends Serializer<ProgressSerializer> {

    @JsonProperty("code")
    private final String code;
    
    public ProgressSerializer(Progress progress) {
        this.id = progress.getId();
        this.code = progress.getCode();
        
    }

    public String getCode() {
        return code;
    }
}

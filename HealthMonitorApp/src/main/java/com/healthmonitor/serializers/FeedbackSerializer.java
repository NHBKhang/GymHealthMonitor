package com.healthmonitor.serializers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.healthmonitor.pojo.Feedback;
import java.util.Date;

public class FeedbackSerializer extends Serializer<FeedbackSerializer> {

    @JsonProperty("code")
    private final String code;
    
    @JsonProperty("comment")
    private final String comment;
    
    @JsonProperty("rating")
    private final Double rating;
    
    @JsonProperty("created_at")
    private final Date createdAt;
    
    public FeedbackSerializer(Feedback feedback) {
        this.id = feedback.getId();
        this.code = feedback.getCode();
        this.comment = feedback.getComment();
        this.rating = feedback.getRating();
        this.createdAt = feedback.getCreatedAt();
    }

    public String getCode() {
        return code;
    }

    public String getComment() {
        return comment;
    }

    public Double getRating() {
        return rating;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
    
}

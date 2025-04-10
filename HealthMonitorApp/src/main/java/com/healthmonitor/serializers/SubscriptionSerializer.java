package com.healthmonitor.serializers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.healthmonitor.pojo.Subscription;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SubscriptionSerializer extends Serializer<SubscriptionSerializer> {
    
    @JsonProperty("code")
    private final String code;
    
    @JsonProperty("status")
    private final String status;
    
    @JsonProperty("start_date")
    private final Date startDate;
    
    @JsonProperty("end_date")
    private final Date endDate;
    
    @JsonProperty("package")
    private final PackageSerializer gymPackage;
    
    public SubscriptionSerializer(Subscription sub) {
        this.id = sub.getId();
        this.code = sub.getCode();
        this.startDate = sub.getStartDate();
        this.endDate = sub.getEndDate();
        this.status = sub.getStatus().getDescription();
        this.gymPackage = new PackageSerializer(sub.getGymPackage());
    }

    public static List<SubscriptionSerializer> fromSubscriptions(List<Subscription> subscriptions) {
        return subscriptions.stream()
                .map(SubscriptionSerializer::new)
                .collect(Collectors.toList());
    }

    public String getCode() {
        return code;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getStatus() {
        return status;
    }

    public PackageSerializer getGymPackage() {
        return gymPackage;
    }
}

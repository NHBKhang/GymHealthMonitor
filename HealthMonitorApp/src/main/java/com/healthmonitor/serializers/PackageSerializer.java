package com.healthmonitor.serializers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.healthmonitor.pojo.Package;
import java.util.List;
import java.util.stream.Collectors;

public class PackageSerializer extends Serializer<PackageSerializer> {

    @JsonProperty("name")
    private final String name;

    @JsonProperty("code")
    private final String code;

    @JsonProperty("duration")
    private final String duration;

    @JsonProperty("price")
    private final Double price;

    @JsonProperty("pt_sessions")
    private final Integer ptSessions;

    @JsonProperty("description")
    private final String description;

    public PackageSerializer(Package pkg) {
        this.id = pkg.getId();
        this.name = pkg.getName();
        this.code = pkg.getCode();
        this.duration = pkg.getDuration().getDescription();
        this.price = pkg.getPrice();
        this.ptSessions = pkg.getPtSessions();
        this.description = pkg.getDescription();
    }

    public static List<PackageSerializer> fromPackages(List<Package> packages) {
        return packages.stream()
                .map(PackageSerializer::new)
                .collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getDuration() {
        return duration;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getPtSessions() {
        return ptSessions;
    }

    public String getDescription() {
        return description;
    }
    
}

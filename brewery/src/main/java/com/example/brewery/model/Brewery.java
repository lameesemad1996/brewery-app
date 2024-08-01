package com.example.brewery.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Brewery {
    private String id;
    private String name;
    @JsonProperty("brewery_type")
    private String breweryType;
    private String city;
    private String state;
    @JsonProperty("website_url")
    private String websiteUrl;
    private String phone;
    @JsonProperty("address_1")
    private String address;
}
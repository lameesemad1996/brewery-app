package com.example.brewery.service;

import com.example.brewery.model.Brewery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class BreweryService {

    private static final Logger logger = LoggerFactory.getLogger(BreweryService.class);
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String API_URL = "https://api.openbrewerydb.org/breweries";

    public List<Brewery> getAllBreweries(int page) {
        ResponseEntity<List<Brewery>> response = restTemplate.exchange(
                API_URL + "?page=" + page,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        logger.info("API Response (All Breweries): {}", response.getBody());
        return response.getBody();
    }

    public List<Brewery> searchBreweries(String name, String city, String state, String type) {
        String url = API_URL + "/search?query=" + name + "&by_city=" + city + "&by_state=" + state + "&by_type=" + type;
        ResponseEntity<List<Brewery>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        logger.info("API Response (Search Breweries): {}", response.getBody());
        return response.getBody();
    }

    public Brewery getBreweryById(String id) {
        Brewery brewery = restTemplate.getForObject(API_URL + "/" + id, Brewery.class);
        logger.info("API Response (Get Brewery By ID): {}", brewery);
        return brewery;
    }
}

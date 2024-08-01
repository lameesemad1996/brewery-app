package com.example.brewery.controller;

import com.example.brewery.model.Brewery;
import com.example.brewery.service.BreweryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/breweries")
public class BreweryController {

    private static final Logger logger = LoggerFactory.getLogger(BreweryController.class);

    private final BreweryService breweryService;

    public BreweryController(BreweryService breweryService) {
        this.breweryService = breweryService;
    }

    @GetMapping
    public List<Brewery> getAllBreweries(@RequestParam int page) {
        logger.info("Fetching all breweries for page: {}", page);
        List<Brewery> breweries = breweryService.getAllBreweries(page);
        logger.info("Found {} breweries on page: {}", breweries.size(), page);
        return breweries;
    }

    @GetMapping("/search")
    public List<Brewery> searchBreweries(
            @RequestParam String name,
            @RequestParam String city,
            @RequestParam String state,
            @RequestParam String type
    ) {
        logger.info("Searching breweries with name: {}, city: {}, state: {}, type: {}", name, city, state, type);
        List<Brewery> breweries = breweryService.searchBreweries(name, city, state, type);
        logger.info("Found {} breweries matching the search criteria", breweries.size());
        return breweries;
    }

    @GetMapping("/{id}")
    public Brewery getBreweryById(@PathVariable String id) {
        logger.info("Fetching brewery with id: {}", id);
        Brewery brewery = breweryService.getBreweryById(id);
        logger.info("Found brewery: {}", brewery);
        return brewery;
    }
}

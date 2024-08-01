package com.example.brewery.controller;

import com.example.brewery.exception.ResourceNotFoundException;
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

    @GetMapping("/by_ids")
    public List<Brewery> getBreweriesByIds(@RequestParam List<String> ids) {
        return breweryService.getBreweriesByIds(ids);
    }

    @GetMapping("/search")
    public List<Brewery> searchBreweries(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String type
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
        if (brewery == null) {
            throw new ResourceNotFoundException("Brewery not found with id: " + id);
        }
        logger.info("Found brewery: {}", brewery);
        return brewery;
    }
}

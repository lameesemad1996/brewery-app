package com.example.brewery.controller;

import com.example.brewery.exception.ResourceNotFoundException;
import com.example.brewery.model.Brewery;
import com.example.brewery.service.BreweryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing breweries.
 * Provides endpoints for fetching, searching, and retrieving breweries.
 */
@RestController
@RequestMapping("/api/breweries")
public class BreweryController {

    private static final Logger logger = LoggerFactory.getLogger(BreweryController.class);

    private final BreweryService breweryService;

    /**
     * Constructs a new BreweryController with the specified BreweryService.
     *
     * @param breweryService the BreweryService to be used by this controller
     */
    public BreweryController(BreweryService breweryService) {
        this.breweryService = breweryService;
    }

    /**
     * Fetches a paginated list of all breweries.
     *
     * @param page the page number to retrieve
     * @return a list of breweries on the specified page
     */
    @GetMapping
    public List<Brewery> getAllBreweries(@RequestParam int page) {
        logger.info("Fetching all breweries for page: {}", page);
        List<Brewery> breweries = breweryService.getAllBreweries(page);
        logger.info("Found {} breweries on page: {}", breweries.size(), page);
        return breweries;
    }

    /**
     * Fetches a list of breweries by their IDs.
     *
     * @param ids the list of brewery IDs to retrieve
     * @return a list of breweries with the specified IDs
     */
    @GetMapping("/by_ids")
    public List<Brewery> getBreweriesByIds(@RequestParam List<String> ids) {
        return breweryService.getBreweriesByIds(ids);
    }

    /**
     * Searches for breweries based on various criteria.
     *
     * @param name  the name of the brewery to search for (optional)
     * @param city  the city of the brewery to search for (optional)
     * @param state the state of the brewery to search for (optional)
     * @param type  the type of the brewery to search for (optional)
     * @return a list of breweries matching the search criteria
     */
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

    /**
     * Fetches a brewery by its ID.
     *
     * @param id the ID of the brewery to retrieve
     * @return the brewery with the specified ID
     * @throws ResourceNotFoundException if no brewery is found with the specified ID
     */
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
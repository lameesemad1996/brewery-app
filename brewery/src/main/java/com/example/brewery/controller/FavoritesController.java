package com.example.brewery.controller;

import com.example.brewery.exception.FavoriteAlreadyExistsException;
import com.example.brewery.exception.FavoriteNotFoundException;
import com.example.brewery.model.Favorite;
import com.example.brewery.service.FavoriteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * REST controller for managing user favorite breweries.
 * Provides endpoints for adding, removing, and fetching user favorites.
 */
@RestController
@RequestMapping("/api/favorites")
public class FavoritesController {

    private static final Logger logger = LoggerFactory.getLogger(FavoritesController.class);

    private final FavoriteService favoriteService;

    /**
     * Constructs a new FavoritesController with the specified FavoriteService.
     *
     * @param favoriteService the FavoriteService to be used by this controller
     */
    public FavoritesController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    /**
     * Fetches a list of favorite breweries for the specified user.
     *
     * @param userId the ID of the user whose favorites are to be fetched
     * @return a ResponseEntity containing a list of the user's favorite breweries
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<Favorite>> getUserFavorites(@PathVariable String userId) {
        logger.info("Fetching favorites for user: {}", userId);
        List<Favorite> favorites = favoriteService.getUserFavorites(userId);
        logger.info("Found {} favorites for user: {}", favorites.size(), userId);
        return ResponseEntity.ok(favorites);
    }

    /**
     * Adds a brewery to the user's list of favorites.
     *
     * @param favorite the Favorite object containing user ID and brewery ID
     * @param bindingResult the BindingResult object to hold validation errors
     * @return a ResponseEntity with the added Favorite object or an error message
     */
    @PostMapping
    public ResponseEntity<?> addFavorite(@Valid @RequestBody Favorite favorite, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessages = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .reduce("", (message1, message2) -> message1 + ", " + message2);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
        }

        try {
            Favorite addedFavorite = favoriteService.addFavorite(favorite);
            return ResponseEntity.ok(addedFavorite);
        } catch (FavoriteAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    /**
     * Removes a brewery from the user's list of favorites.
     *
     * @param userId the ID of the user
     * @param breweryId the ID of the brewery to be removed from favorites
     * @return a ResponseEntity with a success message or an error message
     */
    @DeleteMapping
    public ResponseEntity<?> removeFavorite(@RequestParam String userId, @RequestParam String breweryId) {
        try {
            favoriteService.removeFavorite(userId, breweryId);
            return ResponseEntity.ok("Favorite removed successfully.");
        } catch (FavoriteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}

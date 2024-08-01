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

@RestController
@RequestMapping("/api/favorites")
public class FavoritesController {

    private static final Logger logger = LoggerFactory.getLogger(FavoritesController.class);

    private final FavoriteService favoriteService;

    public FavoritesController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Favorite>> getUserFavorites(@PathVariable String userId) {
        logger.info("Fetching favorites for user: {}", userId);
        List<Favorite> favorites = favoriteService.getUserFavorites(userId);
        logger.info("Found {} favorites for user: {}", favorites.size(), userId);
        return ResponseEntity.ok(favorites);
    }

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

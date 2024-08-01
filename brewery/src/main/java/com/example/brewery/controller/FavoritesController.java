package com.example.brewery.controller;

import com.example.brewery.model.Favorite;
import com.example.brewery.service.FavoriteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

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
    public List<Favorite> getUserFavorites(@PathVariable String userId) {
        logger.info("Fetching favorites for user: {}", userId);
        List<Favorite> favorites = favoriteService.getUserFavorites(userId);
        logger.info("Found {} favorites for user: {}", favorites.size(), userId);
        return favorites;
    }

    @PostMapping
    public Favorite addFavorite(@RequestBody Favorite favorite) {
        logger.info("Adding favorite for user: {}", favorite.getUserId());
        Favorite savedFavorite = favoriteService.addFavorite(favorite);
        logger.info("Added favorite with id: {}", savedFavorite.getId());
        return savedFavorite;
    }

    @DeleteMapping("/{id}")
    public void removeFavorite(@PathVariable Long id) {
        logger.info("Removing favorite with id: {}", id);
        favoriteService.removeFavorite(id);
        logger.info("Removed favorite with id: {}", id);
    }
}

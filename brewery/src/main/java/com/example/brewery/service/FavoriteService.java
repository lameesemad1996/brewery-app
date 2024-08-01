package com.example.brewery.service;

import com.example.brewery.model.Favorite;
import com.example.brewery.repository.FavoriteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {

    private static final Logger logger = LoggerFactory.getLogger(FavoriteService.class);

    private final FavoriteRepository favoriteRepository;

    public FavoriteService(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    public List<Favorite> getUserFavorites(String userId) {
        List<Favorite> favorites = favoriteRepository.findByUserId(userId);
        logger.info("Fetched favorites for user {}: {}", userId, favorites);
        return favorites;
    }

    public Favorite addFavorite(Favorite favorite) {
        Favorite savedFavorite = favoriteRepository.save(favorite);
        logger.info("Adding favorite: " + favorite.getUserId() + ", " + favorite.getBreweryId());
        return savedFavorite;
    }

    public void removeFavorite(String userId, String breweryId) {
        Favorite favorite = favoriteRepository.findByUserIdAndBreweryId(userId, breweryId);
        if (favorite != null) {
            favoriteRepository.delete(favorite);
        }
    }
}

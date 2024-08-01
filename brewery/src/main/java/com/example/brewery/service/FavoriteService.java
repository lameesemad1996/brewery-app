package com.example.brewery.service;

import com.example.brewery.exception.FavoriteNotFoundException;
import com.example.brewery.model.Favorite;
import com.example.brewery.repository.FavoriteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.example.brewery.exception.FavoriteAlreadyExistsException;

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
        // Check if the favorite already exists
        Favorite existingFavorite = favoriteRepository.findByUserIdAndBreweryId(favorite.getUserId(), favorite.getBreweryId());
        if (existingFavorite != null) {
            throw new FavoriteAlreadyExistsException("Favorite already exists for userId: " + favorite.getUserId() + " and breweryId: " + favorite.getBreweryId());
        }
        return favoriteRepository.save(favorite);
    }

    public void removeFavorite(String userId, String breweryId) {
        Favorite favorite = favoriteRepository.findByUserIdAndBreweryId(userId, breweryId);
        if (favorite == null) {
            throw new FavoriteNotFoundException("Favorite does not exist for userId: " + userId + " and breweryId: " + breweryId);
        }
        favoriteRepository.delete(favorite);
    }
}

package com.example.brewery.service;

import com.example.brewery.exception.FavoriteAlreadyExistsException;
import com.example.brewery.exception.FavoriteNotFoundException;
import com.example.brewery.model.Favorite;
import com.example.brewery.repository.FavoriteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing user favorite breweries.
 * Provides methods to add, remove, and fetch user favorites.
 */
@Service
public class FavoriteService {

    private static final Logger logger = LoggerFactory.getLogger(FavoriteService.class);

    private final FavoriteRepository favoriteRepository;

    /**
     * Constructs a new FavoriteService with the specified FavoriteRepository.
     *
     * @param favoriteRepository the FavoriteRepository to be used by this service
     */
    public FavoriteService(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    /**
     * Fetches a list of favorite breweries for the specified user.
     *
     * @param userId the ID of the user whose favorites are to be fetched
     * @return a list of the user's favorite breweries
     */
    public List<Favorite> getUserFavorites(String userId) {
        List<Favorite> favorites = favoriteRepository.findByUserId(userId);
        logger.info("Fetched favorites for user {}: {}", userId, favorites);
        return favorites;
    }

    /**
     * Adds a brewery to the user's list of favorites.
     *
     * @param favorite the Favorite object containing user ID and brewery ID
     * @return the added Favorite object
     * @throws FavoriteAlreadyExistsException if the favorite already exists for the user and brewery
     */
    public Favorite addFavorite(Favorite favorite) {
        // Check if the favorite already exists
        Favorite existingFavorite = favoriteRepository.findByUserIdAndBreweryId(favorite.getUserId(), favorite.getBreweryId());
        if (existingFavorite != null) {
            throw new FavoriteAlreadyExistsException("Favorite already exists for userId: " + favorite.getUserId() + " and breweryId: " + favorite.getBreweryId());
        }
        return favoriteRepository.save(favorite);
    }

    /**
     * Removes a brewery from the user's list of favorites.
     *
     * @param userId the ID of the user
     * @param breweryId the ID of the brewery to be removed from favorites
     * @throws FavoriteNotFoundException if the favorite does not exist for the user and brewery
     */
    public void removeFavorite(String userId, String breweryId) {
        Favorite favorite = favoriteRepository.findByUserIdAndBreweryId(userId, breweryId);
        if (favorite == null) {
            throw new FavoriteNotFoundException("Favorite does not exist for userId: " + userId + " and breweryId: " + breweryId);
        }
        favoriteRepository.delete(favorite);
    }
}

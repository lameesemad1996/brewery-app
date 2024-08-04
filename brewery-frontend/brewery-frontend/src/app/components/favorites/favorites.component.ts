import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { FavoriteService } from '../../services/favorites.service';
import { Favorite } from '../../models/favorite';
import { Brewery } from '../../models/brewery';
import { BreweryService } from '../../services/brewery.service';
import { RouterLink } from '@angular/router';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-favorites',
  standalone: true,
  imports: [CommonModule, HttpClientModule, RouterLink],
  templateUrl: './favorites.component.html',
  styleUrls: ['./favorites.component.scss']
})
export class FavoritesComponent {
  favorites: Favorite[] = [];
  favoriteBreweries: Brewery[] = [];
  userId: string = 'user1'; // Assume a logged-in user ID
  errorMessage: string = ''; // For displaying error messages

  constructor(private favoriteService: FavoriteService, private breweryService: BreweryService) {
    this.loadFavorites();
  }

  loadFavorites(): void {
    this.favoriteService.getFavorites(this.userId).subscribe(
      (favorites: Favorite[]) => {
        this.favorites = favorites;
        if (favorites.length > 0) {
          const breweryIds = favorites.map(fav => fav.breweryId);
          this.breweryService.getBreweriesByIds(breweryIds).subscribe(
            (breweries: Brewery[]) => {
              this.favoriteBreweries = breweries;
            },
            error => {
              this.errorMessage = error;
              console.error('Error fetching breweries:', error);
            }
          );
        } else {
          this.favoriteBreweries = []; // Clear favoriteBreweries if there are no favorites
        }
      },
      error => {
        this.errorMessage = error;
        console.error('Error fetching favorites:', error);
      }
    );
  }

  removeFavorite(userId: string, breweryId: string): void {
    this.favoriteService.removeFavorite(userId, breweryId).subscribe(
      () => {
        console.log('Favorite removed');
        this.updateFavorites(breweryId);
      },
      error => {
        if (error === '') {
          console.log('Favorite removed successfully');
          this.updateFavorites(breweryId);
        } else {
          this.errorMessage = error;
          console.error('Error removing favorite:', error);
        }
      }
    );
  }

  updateFavorites(breweryId: string): void {
    // Update the favorites array
    this.favorites = this.favorites.filter(fav => fav.breweryId !== breweryId);
    // Update the favoriteBreweries array
    this.favoriteBreweries = this.favoriteBreweries.filter(brewery => brewery.id !== breweryId);
  }
}

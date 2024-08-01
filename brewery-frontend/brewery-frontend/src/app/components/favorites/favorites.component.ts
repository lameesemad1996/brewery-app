import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { FavoriteService } from '../../services/favorites.service';
import { Favorite } from '../../models/favorite';
import {Brewery} from "../../models/brewery";
import {BreweryService} from "../../services/brewery.service";
import {RouterLink} from "@angular/router";

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
        this.loadFavorites();
      },
      error => {
        if (error === '') {
          console.log('Favorite removed successfully');
          this.loadFavorites();
        } else {
          this.errorMessage = error;
          console.error('Error removing favorite:', error);
        }
      }
    );
  }
}


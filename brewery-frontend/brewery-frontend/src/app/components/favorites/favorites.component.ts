import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { FavoriteService } from '../../services/favorites.service';
import { Favorite } from '../../models/favorite';
import {Brewery} from "../../models/brewery";

@Component({
  selector: 'app-favorites',
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  templateUrl: './favorites.component.html',
  styleUrls: ['./favorites.component.scss']
})
export class FavoritesComponent {
  favorites: Favorite[] = [];
  userId: string = 'user1'; // Assume a logged-in user ID

  constructor(private favoriteService: FavoriteService) {
    this.loadFavorites();
  }

  loadFavorites(): void {
    this.favoriteService.getFavorites(this.userId).subscribe(data => {
      this.favorites = data;
    });
  }

  removeFavorite(userId: string, breweryId: string): void {
    this.favoriteService.removeFavorite(userId, breweryId).subscribe(() => {
      this.loadFavorites();
    });
  }
}

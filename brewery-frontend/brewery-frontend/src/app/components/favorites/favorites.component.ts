import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { FavoritesService } from '../../services/favorites.service';
import { Favorite } from '../../models/favorite';

@Component({
  selector: 'app-favorites',
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  templateUrl: './favorites.component.html',
  styleUrls: ['./favorites.component.scss']
})
export class FavoritesComponent {
  favorites: Favorite[] = [];
  userId: string = 'user1';

  constructor(private favoritesService: FavoritesService) {
    this.loadFavorites();
  }

  loadFavorites(): void {
    this.favoritesService.getUserFavorites(this.userId).subscribe(data => {
      this.favorites = data;
    });
  }

  removeFavorite(id: number): void {
    this.favoritesService.removeFavorite(id).subscribe(() => {
      this.loadFavorites();
    });
  }
}

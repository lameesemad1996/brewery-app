import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { BreweryService } from '../../services/brewery.service';
import { Brewery } from '../../models/brewery';
import {Favorite} from "../../models/favorite";
import {FavoriteService, FavoriteService as FavoritesService} from "../../services/favorites.service";

@Component({
  selector: 'app-brewery-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './brewery-list.component.html',
  styleUrls: ['./brewery-list.component.scss']
})
export class BreweryListComponent implements OnInit {
  breweries: Brewery[] = [];
  favoriteBreweries: Brewery[] = [];

  name: string = '';
  city: string = '';
  state: string = '';
  type: string = '';
  page: number = 1;
  userId: string = 'user1'; // Assume a logged-in user ID

  constructor(private breweryService: BreweryService, private favoriteService: FavoriteService) {}

  ngOnInit(): void {
    this.loadBreweries();
    this.loadFavorites();
  }

  loadBreweries(): void {
    this.breweryService.getAllBreweries(this.page).subscribe(data => {
      this.breweries = data;
    });
  }

  searchBreweries(): void {
    this.breweryService.searchBreweries(this.name, this.city, this.state, this.type).subscribe(data => {
      this.breweries = data;
    });
  }

  addFavorite(brewery: Brewery): void {
    const favorite: Favorite = { userId: this.userId, breweryId: brewery.id };
    this.favoriteService.addFavorite(favorite).subscribe(() => {
      this.loadFavorites();
    });
  }

  loadFavorites(): void {
    this.favoriteService.getFavorites(this.userId).subscribe(data => {
      this.favoriteBreweries = data
        .map(fav => this.breweries.find(b => b.id === fav.breweryId) as Brewery)
        .filter(brewery => brewery !== undefined);
    });
  }

  removeFavorite(brewery: Brewery): void {
    this.favoriteService.removeFavorite(this.userId, brewery.id).subscribe(() => {
      this.loadFavorites();
    });
  }

  nextPage(): void {
    this.page++;
    this.loadBreweries();
  }

  prevPage(): void {
    if (this.page > 1) {
      this.page--;
      this.loadBreweries();
    }
  }
}

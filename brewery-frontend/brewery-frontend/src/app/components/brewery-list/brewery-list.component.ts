// Import necessary modules and services from Angular core and other libraries
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { BreweryService } from '../../services/brewery.service';
import { Brewery } from '../../models/brewery';
import { Favorite } from '../../models/favorite';
import { FavoriteService } from '../../services/favorites.service';

@Component({
  selector: 'app-brewery-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './brewery-list.component.html',
  styleUrls: ['./brewery-list.component.scss']
})
export class BreweryListComponent implements OnInit {

  // Define properties
  breweries: Brewery[] = []; // Array to hold the list of breweries
  favoriteBreweries: Brewery[] = []; // Array to hold the list of favorite breweries

  // Properties for search criteria and pagination
  name: string = '';
  city: string = '';
  state: string = '';
  type: string = '';
  page: number = 1;
  userId: string = 'user1'; // Assume a logged-in user ID
  errorMessage: string = ''; // For displaying error messages

  // Constructor to inject the necessary services
  constructor(private breweryService: BreweryService, private favoriteService: FavoriteService) {}

  ngOnInit(): void {
    this.loadBreweries(); // Load the initial list of breweries
    this.loadFavorites(); // Load the list of favorite breweries
  }

  // Method to load the list of breweries
  loadBreweries(): void {
    this.breweryService.getAllBreweries(this.page).subscribe(data => {
      this.breweries = data; // Assign the fetched data to the 'breweries' property
    });
  }

  // Method to search for breweries based on search criteria
  searchBreweries(): void {
    this.breweryService.searchBreweries(this.name, this.city, this.state, this.type).subscribe(data => {
      this.breweries = data; // Assign the fetched data to the 'breweries' property
    });
  }

  // Method to add a brewery to the list of favorites
  addFavorite(brewery: Brewery): void {
    const favorite: Favorite = { userId: this.userId, breweryId: brewery.id }; // Create a Favorite object
    this.favoriteService.addFavorite(favorite).subscribe(
      response => {
        console.log('Favorite added:', response); // Log the response
        this.loadFavorites(); // Reload the list of favorite breweries
      },
      error => {
        this.errorMessage = error; // Assign the error message
        console.error('Error adding favorite:', error); // Log the error
      }
    );
  }

  // Method to load the list of favorite breweries
  loadFavorites(): void {
    this.favoriteService.getFavorites(this.userId).subscribe(data => {
      // Map the favorite brewery IDs to the brewery objects
      this.favoriteBreweries = data
        .map(fav => this.breweries.find(b => b.id === fav.breweryId) as Brewery)
        .filter(brewery => brewery !== undefined); // Filter out undefined values
    });
  }

  // Method to remove a brewery from the list of favorites
  removeFavorite(brewery: Brewery): void {
    this.favoriteService.removeFavorite(this.userId, brewery.id).subscribe(
      () => {
        console.log('Favorite removed'); // Log the success message
        this.loadFavorites(); // Reload the list of favorite breweries
      },
      error => {
        if (error === '') {
          console.log('Favorite removed successfully'); // Log the success message
          this.loadFavorites(); // Reload the list of favorite breweries
        } else {
          this.errorMessage = error; // Assign the error message
          console.error('Error removing favorite:', error); // Log the error
        }
      }
    );
  }

  // Method to go to the next page of breweries
  nextPage(): void {
    this.page++; // Increment the page number
    this.loadBreweries(); // Reload the list of breweries
  }

  // Method to go to the previous page of breweries
  prevPage(): void {
    if (this.page > 1) {
      this.page--; // Decrement the page number
      this.loadBreweries(); // Reload the list of breweries
    }
  }
}

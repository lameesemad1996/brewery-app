import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { BreweryService } from '../../services/brewery.service';
import { Brewery } from '../../models/brewery';

@Component({
  selector: 'app-brewery-list',
  standalone: true,
  imports: [CommonModule, HttpClientModule, RouterModule],
  templateUrl: './brewery-list.component.html',
  styleUrls: ['./brewery-list.component.scss']
})
export class BreweryListComponent {
  breweries: Brewery[] = [];
  page: number = 1;

  constructor(private breweryService: BreweryService) {
    this.loadBreweries();
  }

  loadBreweries(): void {
    this.breweryService.getAllBreweries(this.page).subscribe(data => {
      this.breweries = data;
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

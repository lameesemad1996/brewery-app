import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { BreweryService } from '../../services/brewery.service';
import { Brewery } from '../../models/brewery';

@Component({
  selector: 'app-brewery-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './brewery-list.component.html',
  styleUrls: ['./brewery-list.component.scss']
})
export class BreweryListComponent implements OnInit {
  breweries: Brewery[] = [];
  name: string = '';
  city: string = '';
  state: string = '';
  type: string = '';
  page: number = 1;

  constructor(private breweryService: BreweryService) {}

  ngOnInit(): void {
    this.loadBreweries();
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

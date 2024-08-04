// Import necessary modules and services from Angular core and other libraries
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { BreweryService } from '../../services/brewery.service';
import { Brewery } from '../../models/brewery';

@Component({
  selector: 'app-brewery-details',
  standalone: true, // Indicates that this component can operate independently without the need for a parent module
  imports: [CommonModule, HttpClientModule, RouterModule],
  templateUrl: './brewery-details.component.html',
  styleUrls: ['./brewery-details.component.scss']
})
export class BreweryDetailsComponent implements OnInit {
  brewery: Brewery = {} as Brewery;

  // Constructor to inject the necessary services
  constructor(private route: ActivatedRoute, private breweryService: BreweryService) {}

  ngOnInit(): void {
    // Retrieve the 'id' parameter from the current route
    const id = this.route.snapshot.paramMap.get('id');

    // If an 'id' is found in the route parameters, fetch the brewery details using the service
    if (id) {
      this.breweryService.getBreweryById(id).subscribe(data => {
        this.brewery = data; // Assign the fetched data to the 'brewery' property
      });
    } else {
      // Log an error if no 'id' is provided in the route parameters
      console.error('No brewery ID provided');
    }
  }
}

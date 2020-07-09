import { Component, OnInit } from '@angular/core';
import { CarDetails } from 'src/app/models/CarDetails';
import { AdvertisementService } from 'src/app/services/advertisement.service';

@Component({
  selector: 'app-statistics-highest-mileage',
  template: `
  <table class="table table-borderless">
  <thead>
    <tr>           
      <th>Car name</th>
      <th>Class</th>
      <th>Model</th>
      <th>Manufacturer</th>
      <th>Fuel</th>
      <th>Mileage in total</th>
    </tr>
  </thead>
  <tbody>
    <tr *ngFor="let car of cars">           
      <td>{{ car.carName }}</td>
      <td>{{ car.carClass }}</td>
      <td>{{ car.carModelName }}</td>
      <td>{{ car.carManufacturer }}</td>
      <td>{{ car.fuelType }}</td>
      <td>{{ car.mileageNumber }}</td>
    </tr>
  </tbody>
</table>
  `,
  styles: [
  ]
})
export class StatisticsHighestMileageComponent implements OnInit {

  
  cars:CarDetails[];

  constructor(private advertisementService:AdvertisementService) { }

  ngOnInit(): void {
    this.advertisementService.getHighestMileageCars().subscribe(data =>{
      this.cars = data;
    });
  }

}

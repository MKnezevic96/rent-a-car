import { Component, OnInit } from '@angular/core';
import { Car } from 'src/app/models/Car';
import { AdvertisementService } from 'src/app/services/advertisement.service';
import { RentRequest } from 'src/app/models/RentRequest';
import { first } from 'rxjs/operators';
@Component({
  selector: 'app-rent-request',
  templateUrl: './rent-request.component.html',
  styleUrls: ['./rent-request.component.css']
})
export class RentRequestComponent implements OnInit {
  cars: Car[];
  car:Car;
  selectedStartDate:Date;
  selectedEndDate: Date;
  id:number;
  rentrequest: RentRequest;
  constructor( 
    private advertisementService: AdvertisementService,
    ) { }
    selectedCar(name:Car){
      this.car = name;
      console.log(this.car);
    }
    selectStartDate() {
      console.log(this.selectedStartDate);
    }
    selectEndDate() {
      console.log(this.selectedEndDate);
    }
  ngOnInit(): void {
    this.advertisementService.getCar().subscribe(data =>{
      this.cars = data;
    });
  }
  onSubmit() {
    this.rentrequest={carName:this.car.name, startDate: this.selectedStartDate, endDate:this.selectedEndDate, status: false, deleted: false, id:this.id };
    this.advertisementService.addRentRequest(this.rentrequest).pipe(first())
    .subscribe(
        data => {
            console.log('Making rent request successful');
        })
  }
}
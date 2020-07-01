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
  availableCars:boolean = false;
  constructor(
    private advertisementService: AdvertisementService,
    ) { }
    selectedCar(name:Car){
      this.car = name;
    }
    selectStartDate() {
    }
    selectEndDate() {
    }
  ngOnInit(): void {
    this.advertisementService.getCar().subscribe(data =>{
      this.cars = data;
    });
  }
  onSubmit() {
    this.rentrequest={id:1, carName:this.car.name, startDate: this.selectedStartDate, endDate:this.selectedEndDate, status: '', deleted: false , startDateString:null, endDateString:null};
    this.advertisementService.addRentRequest(this.rentrequest).pipe(first())
    .subscribe(
        data => {
        })
  }
  naKlik(carName:string) {
    this.rentrequest={carName:carName, startDate: this.selectedStartDate, endDate:this.selectedEndDate, status: '', deleted: false, id:this.id , startDateString:null, endDateString:null};
    this.advertisementService.addRentRequest(this.rentrequest).pipe(first())
    .subscribe(
        data => {
        })
  }

  getAvailableCars(){
    this.availableCars = !this.availableCars;
    this.advertisementService.getAvailableCars(this.selectedStartDate, this.selectedEndDate).subscribe(data =>{
      this.cars = data;
    });
  }
}

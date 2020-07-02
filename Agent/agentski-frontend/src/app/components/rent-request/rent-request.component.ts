import { Component, OnInit } from '@angular/core';
import { Car } from 'src/app/models/Car';
import { AdvertisementService } from 'src/app/services/advertisement.service';
import { RentRequest } from 'src/app/models/RentRequest';
import { first } from 'rxjs/operators';
import { ActivatedRoute, Router } from '@angular/router';
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
  moreFilters:boolean = false;
  citys:string[];
  city:string;


  constructor(
    private advertisementService: AdvertisementService,
    private route: ActivatedRoute,
    private router: Router
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
      console.log(this.cars);
      var i:number;
      var j:number;
      for(i = 0 ; i < this.cars.length ; i++){
        var flag:boolean = false;
        this.citys = this.citys || [];
        for(j = 0 ; j < this.citys.length ; j++){
          if(this.citys[j] == this.cars[i].town){
            flag = true;
          }
        }
        if(!flag){
          this.citys.push(this.cars[i].town);
        }
      }
      console.log(this.citys);
    });
    // this.cars = this.cars || [];
  }
  onSubmit() {
    //this.rentrequest={carName:this.car.name, startDate: this.selectedStartDate, endDate:this.selectedEndDate, status: false, deleted: false, id:this.id };
    this.rentrequest={id:1, carName:this.car.name, startDate: this.selectedStartDate, endDate:this.selectedEndDate, status: '', deleted: false };
    this.advertisementService.addRentRequest(this.rentrequest).pipe(first())
    .subscribe(
        data => {
        })
  }
  naKlik(carName:string) {
    this.rentrequest={carName:carName, startDate: this.selectedStartDate, endDate:this.selectedEndDate, status: '', deleted: false, id:this.id };
    this.advertisementService.addRentRequest(this.rentrequest).pipe(first())
    .subscribe(
        data => {
          console.log('request sent');
          alert('Request sent');
          this.router.navigateByUrl('index');
        })
  }

  getAvailableCars(){
    this.advertisementService.getAvailableCars(this.selectedStartDate, this.selectedEndDate, this.city).subscribe(data =>{
      this.cars = data;
    });
    this.availableCars = !this.availableCars;
  }

  filterCars(){

    
    if(typeof this.selectedStartDate == 'undefined' || typeof this.selectedEndDate == 'undefined'){
      alert('You have not selected a date for request')
    }else{
      this.advertisementService.setSelectedStartDate(this.selectedStartDate);
      this.advertisementService.setSelectedEndDate(this.selectedEndDate);
      this.advertisementService.getAvailableCars(this.selectedStartDate, this.selectedEndDate, this.city).subscribe(data =>{
        this.cars = data;
      });
      this.advertisementService.setAvailableCars(this.cars);
      this.advertisementService.setCity(this.city);
      this.router.navigate(['filterCars'], {relativeTo:this.route.parent});
    }
  }



  getSelectedStartDate(){
    return this.selectedStartDate;
  }

  getSelectedEndDate(){
    return this.selectedEndDate;
  }
}

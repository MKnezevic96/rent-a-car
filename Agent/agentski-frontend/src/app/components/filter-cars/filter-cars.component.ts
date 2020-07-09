import { Component, OnInit } from '@angular/core';
import { AdvertisementService } from 'src/app/services/advertisement.service';
import { AdminService } from 'src/app/services/admin.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FuelType } from 'src/app/models/FuelType';
import { CarModels } from 'src/app/models/CarModels';
import { Pricing } from 'src/app/models/Pricing';
import { CarClass } from 'src/app/models/CarClass';
import { TransmissionType } from 'src/app/models/TransmissionType';
import { Manufacturer } from 'src/app/models/Manufacturer';
import { RentRequest } from 'src/app/models/RentRequest';
import { first } from 'rxjs/operators';
import { Car } from 'src/app/models/Car';
import { RentRequestComponent } from '../rent-request/rent-request.component';

@Component({
  selector: 'app-filter-cars',
  templateUrl: './filter-cars.component.html',
  styleUrls: ['./filter-cars.component.css']
})
export class FilterCarsComponent implements OnInit {

  cars: Car[];
  carsAvailable: Car[];
  carsFiltered: Car[];
  fuelTypes: FuelType[];
  carModels:CarModels[];
  carClasses:CarClass[];
  tTypes:TransmissionType[];
  manufacs:Manufacturer[];
  ft:FuelType;
  cm:CarModels;
  cc:CarClass;
  ma:Manufacturer;
  tt:TransmissionType;
  filterCars:boolean = false;
  rentrequest: RentRequest;
  selectedStartDate:Date;
  selectedEndDate: Date;
  id:number;
  city:string;

  constructor(
    private adminService: AdminService,
    private advertisementService: AdvertisementService,
    private route: ActivatedRoute,
    private router: Router,
    //private rentRequestComponent: RentRequestComponent
  ) { }

  ngOnInit(): void {

    this.adminService.getFuelT().subscribe(data =>{
      this.fuelTypes = data;
    });
    this.adminService.getCarModels().subscribe(data =>{
      this.carModels = data;
    });
    this.adminService.getCarC().subscribe(data =>{
      this.carClasses = data;
    });
    this.adminService.getTransmis().subscribe(data =>{
      this.tTypes = data;
    });
    this.adminService.getManufac().subscribe(data =>{
      this.manufacs = data;
    });
    this.selectedStartDate = this.advertisementService.getSelectedStartDate();
    this.selectedEndDate = this.advertisementService.getSelectedEndDate();
    this.city = this.advertisementService.getCity();
    //this.carsAvailable = this.advertisementService.getAvailable();
    console.log(this.carsAvailable);

    if(typeof this.selectedStartDate == 'undefined' || typeof this.selectedEndDate == 'undefined'){
      alert('No selected start and end date');
      this.router.navigateByUrl('index');
    }
  }

  selectedCarModel(name:CarModels){
    this.cm = name;
    console.log(this.carModels);
  }

  filter(){
    var fuel;
    var tran;
    var manu;
    var clas;
    var model;
    if(typeof this.ft == 'undefined'){
      fuel = 'i';
    }else{
      fuel = this.ft.name;
    }
    if(typeof this.tt == 'undefined'){
      tran = 'i';
    }else{
      tran = this.tt.name;
    }
    if(typeof this.ma == 'undefined'){
      manu = 'i';
    }else{
      manu = this.ma.name;
    }
    if(typeof this.cc == 'undefined'){
      clas = 'i';
    }else{
      clas = this.cc.name;
    }
    if(typeof this.cm == 'undefined'){
      model = 'i';
    }else{
      model = this.cm.name;
    }
    this.advertisementService.getFilteredCars(fuel, tran, manu, clas, model).subscribe(data =>{
      this.carsFiltered = data;
      console.log(this.carsFiltered);
      this.getAvailableCars();
      //this.setCars();
      this.filterCars = !this.filterCars;
    });
  }
  getAvailableCars(){
    this.advertisementService.getAvailableCars(this.selectedStartDate, this.selectedEndDate, this.city).subscribe(data =>{
      this.carsAvailable = data;
      this.setCars();
    });
  }

  setCars(){
    var i;
    var j;
    var kola:Car[];
    for(i = 0 ; i < this.carsFiltered.length ; i++){
      console.log(this.carsFiltered[i]);
      for(j = 0 ; j < this.carsAvailable.length ; j++){
        console.log(this.carsAvailable[j]);
        if(this.carsFiltered[i].id == this.carsAvailable[j].id){
          kola = kola || [];
          kola.push(this.carsFiltered[i]);
          console.log(this.cars);
        }
      }
    }
    this.cars = kola;
    // this.carsFiltered.forEach(el => {
    //   console.log(el.name);
    //   this.carsAvailable.forEach(e =>{
    //     console.log(el.name);
    //     if(el.id == e.id){
    //       this.cars.push(el);
    //     }
    //   });
    // });
  }


  naKlik(carName:string) {
    this.rentrequest={carName:carName, startDate: this.selectedStartDate, endDate:this.selectedEndDate, status: '', deleted: false, id:this.id };
    this.advertisementService.addRentRequest(this.rentrequest).pipe(first())
    .subscribe(
        data => {
          console.log('request sent');
          alert('Request sent');
          this.router.navigateByUrl('index');
        }, error=>{
          if(error.status == 403){
            alert('This action has been blocked by admin');
          }
        })
  }

}

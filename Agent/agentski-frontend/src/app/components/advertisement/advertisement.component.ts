import { Component, OnInit } from '@angular/core';
import { CarModels } from 'src/app/models/CarModels';
import { FuelType } from 'src/app/models/FuelType';
import { AdvertisementService } from 'src/app/services/advertisement.service';
import { AdminService } from 'src/app/services/admin.service';
import { first } from 'rxjs/operators';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Pricing } from 'src/app/models/Pricing';
import { ActivatedRoute, Router } from '@angular/router';
//import * as moment from 'moment';
// import {ModalDismissReasons, NgbDatepickerConfig, NgbModal} from '@ng-bootstrap/ng-bootstrap';
// import {faCalendar, faWindowClose, faPlus, faMinus} from '@fortawesome/free-solid-svg-icons';
@Component({
  selector: 'app-advertisement',
  templateUrl: './advertisement.component.html',
  styleUrls: ['./advertisement.component.css']
})
export class AdvertisementComponent implements OnInit {
  model: CarModels[];
  fuelType: FuelType[];
  //fuelTyp: string;
  nameAdvertisement: string;
  namePricing:string;
  milage: number;
  distancelimit: number;
  regularprice: number;
  overuseprice: number;
  collisiondamage: number;
  discountdays: number;
  discountperc:  number;
  pricing: Pricing;
  ft:FuelType;
  selectedStartDate:Date;
  selectedEndDate: Date;
  carModels:CarModels[];
  cm:CarModels;

  //minDate = moment(new Date()).format('YYYY-MM-DD');  //current
  constructor(
    private adminService: AdminService, 
    private advertisementService: AdvertisementService,
    private route: ActivatedRoute,
    private router: Router
  ) {
  }
  selectedFuelType(name:FuelType){
    this.ft = name;
    console.log(this.fuelType);
  }
  selectedCarModel(name:CarModels){
    this.cm = name;
    console.log(this.carModels);
  }
  selectStartDate() {
    console.log(this.selectedStartDate);
  }
  selectEndDate() {
    console.log(this.selectedEndDate);
  }
  ngOnInit(): void {
    // this.adminService.getCarModel().subscribe(data =>{
    //   this.model = data;
    // });
    this.adminService.getFuelT().subscribe(data =>{
      this.fuelType = data;
    });
    this.adminService.getCarModels().subscribe(data =>{
      this.carModels = data;
    });
  }
  onSubmit() {
    this.pricing={distanceLimit: this.distancelimit, regularPrice: this.regularprice, overusePrice: this.overuseprice, collisionDamage: this.collisiondamage, discountDays: this.discountdays, discountPercent: this.discountperc, name: this.namePricing, deleted:false};
    console.log(this.ft);
    console.log(this.cm);

    console.log(this.pricing);
    this.advertisementService.addPricing(this.pricing).pipe(first())
    .subscribe(
        data => {
            console.log('Making advertisement successful');
            this.makeAdvertisement();
        })

  }

  makeAdvertisement(){
    this.advertisementService.addCar(this.namePricing, this.cm.name, this.ft.name, this.milage, this.nameAdvertisement, this.selectedStartDate, this.selectedEndDate).pipe(first())
    .subscribe(
        data => {
            console.log('Making advertisement successful');
        })

  }

  
}

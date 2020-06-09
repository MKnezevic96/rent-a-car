import { Component, OnInit } from '@angular/core';
import { CarModels } from 'src/app/models/CarModels';
import { AdminService } from 'src/app/services/admin.service';

import { ActivatedRoute, Router } from '@angular/router';
import { Manufacturer } from 'src/app/models/Manufacturer';
import { TransmissionType } from 'src/app/models/TransmissionType';
import { CarClass } from 'src/app/models/CarClass';
import { FuelType } from 'src/app/models/FuelType';

@Component({
  selector: 'app-car-models',
  template: `
    <div class="container fluid">
    <div class="row">
      <div class="col-sm-5">
       <div class="card" style="margin: 30px 0px 30px 0px;" >
        
       <ng-container *ngIf="carModelAdd">
       <div class="card-header" id="addNew"> Add new </div>
      <div class="card-body">
      
          <div id="cModel">
              <label>Enter car model name: </label>
              <input class="form-control form-control-sm" type="text" id="nameCM" name="nameCM" [(ngModel)]="nameCM"><p></p>
            Select car class:
            <select class="form-control form-control-sm" [(ngModel)]="caarc">
              <option [ngValue]="caarc" *ngFor="let caarc of carClass" (click)="selectedCarClass(caarc)">{{caarc.name}} </option>
            </select><p></p>
            Select transmission type:
            <select class="form-control form-control-sm" [(ngModel)]="trrans">
              <option [ngValue]="trrans" *ngFor="let trrans of transmissionType" (click)="selectedTransmission(trrans)">{{trrans.name}}</option>
            </select><p></p>
            Select manufacturer:
            <select class="form-control form-control-sm" [(ngModel)]="maanu">
              <option [ngValue]="maanu" *ngFor="let maanu of manufacturer" (click)="selectedManufacturer(maanu)">{{maanu.name}}</option>
            </select><p></p>
            <button class="btn btn-primary" (click)="addCM()">Submit</button>
          </div>
          </div>
        </ng-container>

        <ng-container *ngIf="carModelUpdate">
        <div class="card-header" id="update"> Update</div>
      <div class="card-body">
        <div id="carMod">
            <label>Update car model name: </label>
            <input class="form-control form-control-sm" type="text" id="nameCMU" name="nameCMU" [(ngModel)]="nameCM">
            <div class="form-group">
                  <div class="form-check"> 
                    <input type="checkbox" id="actCM" name="actCM" [(ngModel)]="actCM">
                    <label for="actCM">Activate</label>
                  </div>
                </div>
            <button class="btn btn-primary" (click)="updateCM()">Submit</button>
            
        </div>
        </div>
        </ng-container>   
    </div>
  </div>

    <ng-container>
    <table class="table table-borderless">
         <thead>
           <tr>           
             <th>Name</th>
             <th>Status</th>
           </tr>
         </thead>
         <tbody>
           <tr *ngFor="let model of carModels">           
             <td>{{ model.name }}</td>
             <td *ngIf="!model.deleted">Active</td>
             <td *ngIf="model.deleted">Deleted</td>
             <td><button class="btn btn-primary" (click)="deleteCarModel(model.name)">Delete</button></td>
             <td><button class="btn btn-primary" (click)="updateCarModel(model.name)">Update</button></td>
         </tr>
         </tbody>
       </table>
 </ng-container>
    </div>
  `,
  styles: [
  ]
})
export class CarModelsComponent implements OnInit {

  maanu:Manufacturer;
  trrans:TransmissionType;
  caarc:CarClass
  nameCM: string;
  nameCMU: string;
  actCM: boolean;

  carModelAdd:boolean=true;
  carModelUpdate:boolean=false;

  manufacturer:Manufacturer[];
  transmissionType:TransmissionType[];
  carClass:CarClass[];
  fuelType:FuelType[];
  carModels: CarModels[];


  constructor(private adminService: AdminService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    this.adminService.getCarModels().subscribe(data => {
      this.carModels = data;
    });
    this.adminService.getManufac().subscribe(data =>{
      this.manufacturer = data;
    });
    this.adminService.getTransmis().subscribe(data =>{
      this.transmissionType = data;
    });
    this.adminService.getCarC().subscribe(data =>{
      this.carClass = data;
    });
    this.adminService.getFuelT().subscribe(data =>{
      this.fuelType = data;
    });
  }


  selectedCarClass(name:CarClass){
    this.caarc = name;
  }

  selectedTransmission(name:TransmissionType){
    this.trrans = name;
  }

  selectedManufacturer(name:Manufacturer){
    this.maanu = name;
  }

  updateCarModel(name:string) {
    this.nameCM = name;
    this.nameCMU = name;

    this.carModelAdd=false;
    this.carModelUpdate=true;
  }

  deleteCarModel(name: string) {
    this.adminService.deleteCarModel(name).subscribe((data: string) => {
      this.adminService.getCarModels().subscribe(data => {
        this.carModels = data;
      });
    })
  }

  addCM(){
    this.adminService.addCM(this.nameCM, this.caarc.name, this.trrans.name, this.maanu.name).subscribe((data:CarModels)=>{
      console.log(data);
    });
    this.adminService.getCarModels().subscribe(data => {
      this.carModels = data;
    });
    
  }

  updateCM(){

    this.adminService.updateCarModel(this.nameCMU, this.nameCM, this.actCM).subscribe((data:string)=>{
      this.adminService.getCarModels().subscribe(data => {
        this.carModels = data;
      });
    })
    this.carModelAdd=true;
    this.carModelUpdate=false;
    this.nameCM = "";
    this.nameCMU = "";
  }


}

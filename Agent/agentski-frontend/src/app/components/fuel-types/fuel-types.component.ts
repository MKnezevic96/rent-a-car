import { Component, OnInit } from '@angular/core';
import { FuelType } from 'src/app/models/FuelType';
import { AdminService } from 'src/app/services/admin.service';

@Component({
  selector: 'app-fuel-types',
  template: `

  <div class="container fluid">
    <div class="row">
      <div class="col-sm-5">
       <div class="card" style="margin: 30px 0px 30px 0px;" >
        
       <ng-container *ngIf="fuelTypeAdd">
       <div class="card-header" id="addNew"> Add new </div>
      <div class="card-body">
      
      <div id="fuelType" class="form group">
          <p>Enter fuel type name: </p>
          <input class="form-control form-control-sm" type="text" id="nameFT" name="nameFT" [(ngModel)]="nameFT">
          <p></p>
          <button class="btn btn-primary" (click)="addFT()">Submit</button>
      </div>
          
          </div>
        </ng-container>

        <ng-container *ngIf="fuelTypeUpdate">
        <div class="card-header" id="update"> Update</div>
      <div class="card-body">

            <div id="fuelTypT">
                <p>Update fuel type name: </p>
                <input class="form-control form-control-sm" type="text" id="nameFTU" name="nameFTU" [(ngModel)]="nameFT">
                <div class="form-group">
                  <div class="form-check">
                    <input type="checkbox" id="actFT" name="actFT" [(ngModel)]="actFT">
                    <label for="actCM">Activate</label> 
                  </div>
                </div>
                <button class="btn btn-primary" (click)="updateFT()">Submit</button>
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
         <tr *ngFor="let fuelT of fuelType">           
           <td>{{ fuelT.name }}</td>
           <td *ngIf="!fuelT.deleted">Active</td>
           <td *ngIf="fuelT.deleted">Deleted</td>
           <td><button class="btn btn-primary" (click)="deleteFuelType(fuelT.name)">Delete</button></td>
          <td><button class="btn btn-primary" (click)="updateFuelType(fuelT.name)">Update</button></td>
       </tr>
       </tbody>
     </table>
</ng-container>
</div>
  `,
  styles: [
  ]
})
export class FuelTypesComponent implements OnInit {


  nameFT:string;
  nameFTU:string;
  actFT:boolean;

  fuelTypeAdd:boolean=true;
  fuelTypeUpdate:boolean=false;

  fuelType:FuelType[];

  constructor(private adminService: AdminService) { }

  ngOnInit(): void {
    this.adminService.getFuelT().subscribe(data =>{
      this.fuelType = data;
    });
  }


  deleteFuelType(name:string){
    this.adminService.deleteFuelType(name).subscribe((data:string)=>{
      this.adminService.getFuelT().subscribe(data =>{
        this.fuelType = data;
      });
    })
  }

  updateFuelType(name:string){
    this.nameFT = name;
    this.nameFTU = name;
    this.fuelTypeAdd=false;
    this.fuelTypeUpdate=true;
  }

  updateFT(){
    this.adminService.updateFT(this.nameFTU, this.nameFT, this.actFT).subscribe((data:string)=>{
      this.adminService.getFuelT().subscribe(data =>{
        this.fuelType = data;
      });
    })
      this.fuelTypeAdd=true;
      this.fuelTypeUpdate=false;
      this.nameFT = "";
      this.nameFTU = "";
  }

  addFT(){
    console.log(this.nameFT);
    this.adminService.addFT(this.nameFT).subscribe((data:string)=>{
      console.log(data);
    },
    error =>{
      alert('FuelType type with this name already exists');
    })
  }


}

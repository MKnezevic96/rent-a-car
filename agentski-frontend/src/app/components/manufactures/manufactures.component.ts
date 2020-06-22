import { Component, OnInit } from '@angular/core';
import { AdminService } from 'src/app/services/admin.service';
import { Manufacturer } from 'src/app/models/Manufacturer';

@Component({
  selector: 'app-manufactures',
  template: `
  <div class="container fluid">
  <div class="row">
    <div class="col-sm-5">
      <div class="card" style="margin: 30px 0px 30px 0px;" >
    
      <ng-container *ngIf="manufacturesAdd">
      <div class="card-header" id="addNew"> Add new </div>
       
      <div class="card-body">
      <div id="manufac">
          <label>Enter manufacturer name: </label>
          <input class="form-control form-control-sm" type="text" id="nameM" name="nameM" [(ngModel)]="nameM">
          <p></p>
          <button class="btn btn-primary" (click)="addM()">Submit</button>
      </div>
      </div>
    </ng-container>

    <ng-container *ngIf="manufacturesUpdate">
    <div class="card-header" id="update"> Update</div>
    <div class="card-body">
    <div id="carCU">
        <label>Update manufacturer name: </label>
        <input class="form-control form-control-sm" type="text" id="nameMU" name="nameMU" [(ngModel)]="nameM">
        <div class="form-group">
          <div class="form-check">
          <input type="checkbox" id="actMan" name="actMan" [(ngModel)]="actMan">
              <label for="actCM">Activate</label> 
          </div>
        </div>
        <button class="btn btn-primary" (click)="updateM()">Submit</button>
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
                  <tr *ngFor="let manufac of manufacturer">           
                    <td>{{ manufac.name }}</td>
                    <td *ngIf="!manufac.deleted">Active</td>
                    <td *ngIf="manufac.deleted">Deleted</td>
                    <td><button class="btn btn-primary" (click)="deleteManufacturer(manufac.name)">Delete</button></td>
                    <td><button class="btn btn-primary" (click)="updateManufacturer(manufac.name)">Update</button></td>
                </tr>
                </tbody>
              </table>
</ng-container>
</div>
  `,
  styles: [
  ]
})
export class ManufacturesComponent implements OnInit {


  nameM:string;
  nameMU:string;
  maanu:Manufacturer;
  manufacturer:Manufacturer[];
  actMan:boolean;

  manufacturesAdd:boolean=true;
  manufacturesUpdate:boolean=false;




  constructor(private adminService: AdminService) { }

  ngOnInit(): void {
    this.adminService.getManufac().subscribe(data =>{
      this.manufacturer = data;
    });
  }

  addM(){
    console.log(this.nameM);
    this.adminService.addM(this.nameM).subscribe((data:string)=>{
      this.adminService.getManufac().subscribe(data =>{
        this.manufacturer = data;
      });
    },
    error =>{
      alert('Manufacturer with this name already exists');
    })
  }

  deleteManufacturer(name: string){
    this.adminService.deleteManufacturer(name).subscribe((data:string)=>{
      this.adminService.getManufac().subscribe(data =>{
        this.manufacturer = data;
      });
    })
  }

  updateManufacturer(name:string){
    this.nameM = name;
    this.nameMU = name;
    this.manufacturesAdd=false;
    this.manufacturesUpdate=true;
  }

  updateM(){
    this.adminService.updateM(this.nameMU, this.nameM, this.actMan).subscribe((data:string)=>{
      this.adminService.getManufac().subscribe(data =>{
        this.manufacturer = data;
      });
    })

  this.manufacturesAdd=true;
  this.manufacturesUpdate=false;
  this.nameM = "";
  this.nameMU = "";
  }

}

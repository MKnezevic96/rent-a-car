import { Component, OnInit } from '@angular/core';
import { AdminService } from 'src/app/services/admin.service';
import { CarClass } from 'src/app/models/CarClass';

@Component({
  selector: 'app-car-classes',
  template: `
  <div class="container fluid">
    <div class="row">
      <div class="col-sm-5">
        <div class="card" style="margin: 30px 0px 30px 0px;" >
      
        <ng-container *ngIf="carClassesAdd">
        <div class="card-header" id="addNew"> Add new </div>
         
        <div class="card-body">
          <div id="carC">
              <label>Enter car class name: </label>
              <input class="form-control form-control-sm" type="text" id="nameCC" name="nameCC" [(ngModel)]="nameCC">
              <p></p>
              <button class="btn btn-primary" (click)="addCC()">Submit</button>
          </div>
        </div>
      </ng-container>

      <ng-container *ngIf="carClassesUpdate">
      <div class="card-header" id="update"> Update</div>
      <div class="card-body">
      <div id="carCU">
          <label>Update car class name: </label>
          <input class="form-control form-control-sm" type="text" id="nameCCU" name="nameCCU" [(ngModel)]="nameCC">
          <div class="form-group">
            <div class="form-check">
               <input type="checkbox" id="actCC" name="actCC" [(ngModel)]="actCC">
                <label for="actCM">Activate</label> 
            </div>
          </div>
          <button class="btn btn-primary" (click)="updateCC()">Submit</button>
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
                  <tr *ngFor="let carc of carClass">           
                    <td>{{ carc.name }}</td>
                    <td *ngIf="!carc.deleted">Active</td>
                    <td *ngIf="carc.deleted">Deleted</td>
                    <td><button class="btn btn-primary" (click)="deleteCarClass(carc.name)">Delete</button></td>
                    <td><button class="btn btn-primary" (click)="updateCarClass(carc.name)">Update</button></td>
                </tr>
                </tbody>
              </table>
</ng-container>
  </div>
  `,
  styles: [
  ]
})
export class CarClassesComponent implements OnInit {



  nameCC:string;
  nameCCU:string;
  actCC:boolean;
  caarc:CarClass;
  carClass:CarClass[];

  carClassesAdd:boolean=true;
  carClassesUpdate:boolean=false;



  constructor(private adminService: AdminService) { }

  ngOnInit(): void {
    this.adminService.getCarC().subscribe(data =>{
      this.carClass = data;
    });
  }

  addCC(){
    console.log(this.nameCC);
    this.adminService.addCC(this.nameCC).subscribe((data:string)=>{
      this.adminService.getCarC().subscribe(data =>{
        this.carClass = data;
      });
    },
    error =>{
      alert('Car class with this name already exists');
    })
  }

  updateCC(){
    this.adminService.updateCC(this.nameCCU, this.nameCC, this.actCC).subscribe((data:string)=>{
      this.adminService.getCarC().subscribe(data =>{
        this.carClass = data;
      });

    this.carClassesAdd=true;
    this.carClassesUpdate=false;
    this.nameCC="";
    this.nameCCU="";
    })
  }

  updateCarClass(name:string){
    this.nameCC = name;
    this.nameCCU = name;
    this.carClassesAdd=false;
    this.carClassesUpdate=true;
  }

  deleteCarClass(name:string){
    this.adminService.deleteCarClass(name).subscribe((data:string)=>{
      this.adminService.getCarC().subscribe(data =>{
        this.carClass = data;
      });
    })
  }

}

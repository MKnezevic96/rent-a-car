import { Component, OnInit } from '@angular/core';
import { AdminService } from 'src/app/services/admin.service';
import { TransmissionType } from 'src/app/models/TransmissionType';

@Component({
  selector: 'app-transmission-types',
  template: `
  <div class="container fluid">
  <div class="row">
    <div class="col-sm-5">
      <div class="card" style="margin: 30px 0px 30px 0px;" >
    
      <ng-container *ngIf="transmissionTypesAdd">
      <div class="card-header" id="addNew"> Add new </div>
       
      <div class="card-body">
      <div id="transmis">
      <label>Enter transmission type name: </label>
      <input  class="form-control form-control-sm" type="text" id="nameTT" name="nameTT" [(ngModel)]="nameTT">
      <p></p>
      <button class="btn btn-primary" (click)="addTT()">Submit</button>
  </div>
      </div>
    </ng-container>

    <ng-container *ngIf="transmissionTypesUpdate">
    <div class="card-header" id="update"> Update</div>
    <div class="card-body">
    <div id="transmisU">
        <label>Update transmission type name: </label>
        <input class="form-control form-control-sm" type="text" id="nameTTU" name="nameTTU" [(ngModel)]="nameTT">
        <div class="form-group">
          <div class="form-check">
          <input type="checkbox" id="actTT" name="actTT" [(ngModel)]="actTT">
              <label for="actCM">Activate</label> 
          </div>
        </div>
        <button class="btn btn-primary" (click)="updateTT()">Submit</button>
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
  <tr *ngFor="let transmis of transmissionType">           
    <td>{{ transmis.name }}</td>
    <td *ngIf="!transmis.deleted">Active</td>
    <td *ngIf="transmis.deleted">Deleted</td>
    <td><button class="btn btn-primary" (click)="deleteTransmissionType(transmis.name)">Delete</button></td>
    <td><button class="btn btn-primary" (click)="updateTransmissionType(transmis.name)">Update</button></td>
</tr>
</tbody>
</table>
</ng-container>
</div>
  `,
  styles: [
  ]
})
export class TransmissionTypesComponent implements OnInit {


  nameTTU:string;
  nameTT:string;
  trrans:TransmissionType;
  actTT:boolean;

  transmissionType:TransmissionType[];
  transmissionTypesAdd:boolean=true;
  transmissionTypesUpdate:boolean=false;


  constructor(private adminService: AdminService) { }

  ngOnInit(): void {
    this.adminService.getTransmis().subscribe(data =>{
      this.transmissionType = data;
    });
  }

  updateTT(){
    this.adminService.updateTT(this.nameTTU, this.nameTT, this.actTT).subscribe((data:string)=>{
      this.adminService.getTransmis().subscribe(data =>{
        this.transmissionType = data;
      });
    })
    this.transmissionTypesAdd=true;
    this.transmissionTypesUpdate=false;
    this.nameTT = "";
    this.nameTTU = "";
  }

  addTT(){
    console.log(this.nameTT);
    this.adminService.addTT(this.nameTT).subscribe((data:string)=>{
      this.adminService.getTransmis().subscribe(data =>{
        this.transmissionType = data;
      });
    },
    error =>{
      alert('Transmission type with this name already exists');
    })
  }

  deleteTransmissionType(name:string){
    this.adminService.deleteTransmissionType(name).subscribe((data:string)=>{
      this.adminService.getTransmis().subscribe(data =>{
        this.transmissionType = data;
      });
    })
  }

  updateTransmissionType(name:string){
    this.nameTT = name;
    this.nameTTU = name;
    this.transmissionTypesAdd=false;
    this.transmissionTypesUpdate=true;
  }
}

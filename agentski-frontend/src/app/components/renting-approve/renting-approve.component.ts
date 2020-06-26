import { Component, OnInit } from '@angular/core';
import { RentRequest } from 'src/app/models/RentRequest';
import { AdvertisementService } from 'src/app/services/advertisement.service';



@Component({
  selector: 'app-renting-approve',
  //templateUrl: './renting-approve.component.html',
  template: `
   <div class="container fluid">
   <ng-container>
          <table class="table table-borderless">
              <thead>
                <tr>           
                  <th>Car</th>
                  <th>Start date</th>
                  <th>End date</th>
                  <th>Status</th>
                </tr>
              </thead>
              <tbody>
                <tr *ngFor="let rr of requests">           
                  <td>{{ rr.carName }}</td>
                  <td>{{ rr.startDate }}</td>
                  <td>{{ rr.endDate }}</td>
                  <td>{{ rr.status }}</td>
                  <td><button class="btn btn-primary" (click)="approve(rr.id)">Approve rent request</button></td>
                  <td><button class="btn btn-primary" (click)="reject(rr.id)">Reject rent request</button></td>

              </tr>
              </tbody>
            </table>
      </ng-container>
   </div>
  `,
  styles: [
  ]
})
export class RentingApproveComponent implements OnInit {

  requests: RentRequest[];

  constructor(private advertisementService: AdvertisementService) { }

  ngOnInit(): void {
    this.advertisementService.getRentRequests().subscribe(data =>{
      this.requests = data;
      console.log(data);
    });
  }
     approve(id:number){
     this.advertisementService.approve(id).subscribe((data:number)=>{
       console.log(data);
     });

 }

  reject(id:number){
      this.advertisementService.reject(id).subscribe((data:number)=>{
      console.log(data);
  });

 }
 }

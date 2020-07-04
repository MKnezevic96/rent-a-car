import { Component, OnInit } from '@angular/core';
import { RentingReportService } from 'src/app/services/renting-report.service';
import { RentRequest } from 'src/app/models/RentRequest';
import { first } from 'rxjs/internal/operators/first';
import { UserService } from 'src/app/security/user.service';

@Component({
  selector: 'app-renting-report',
  template: `

  <nav class="navbar navbar-expand-lg navbar-light bg-light">
  <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav">
          <li class="nav-item active">
              <a class="nav-link">Home <span class="sr-only">(current)</span></a>
          </li>
      </ul>
  </div>
  <button class="btn btn-primary" style="margin-top: 0 !important;" (click)="logout()">Log out</button>
</nav>

<div class="container fluid">
  <div class="form-row">
     <div class="col-md-6 mb-3">
      <form id="reportForm">
      <p></p>
      <label>Select renting request id:</label>
            <select id="rentingInstanceId" class="form-control" [(ngModel)]="request" name="request">
              <option [ngValue]="request" *ngFor="let request of rentingRequests"  (click)="selectedRequest(request)">{{request.carName}} </option>
            </select><p></p>
          <label>Enter mileage </label>
          <input class="form-control" type="number" min="0" id="milage" name="mileage" [(ngModel)]="mileage"><p></p>
        <label> Input renting report information: </label>
        <textarea rows="15" class="form-control" type="input" id="report" name="report" [(ngModel)]="report"></textarea> 
        <p></p>
        <button class="btn btn-primary" (click)="submitReport()">Submit</button> 
      </form>
       </div> 
      </div>
      </div>
  `,
  styles: [
  ]
})
export class RentingReportComponent implements OnInit {

  request:RentRequest;
  mileage:number;
  report:string;
  rentingRequests:RentRequest[];
  selectedLevel;

  constructor(private rentingReportService: RentingReportService,   
      private userService: UserService
    ) { }

  ngOnInit(): void {
    this.rentingReportService.getRentingRequests().subscribe(data => {
      this.rentingRequests = data;
    });
  }


  selectedRequest(request:RentRequest){
    this.request = request;
  }

  submitReport(){
    this.rentingReportService.addNewRentingReport(this.mileage,this.report, this.request.id).pipe(first())
    .subscribe(
        data => {
          console.log(data)
        })
    }  
    
    logout(){
      this.userService.logout().subscribe(data =>{
      });
    }

}

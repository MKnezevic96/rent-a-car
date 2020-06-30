import { Component, OnInit } from '@angular/core';
import { RentingReportService } from 'src/app/services/renting-report.service';
import { RentRequest } from 'src/app/models/RentRequest';
import { first } from 'rxjs/internal/operators/first';
import { RentingReport } from 'src/app/models/RentingReport';

@Component({
  selector: 'app-renting-report',
  template: `
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

  constructor(private rentingReportService: RentingReportService) { }

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
        })
    }  
    
  

}

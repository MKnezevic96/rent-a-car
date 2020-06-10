import { Component, OnInit } from '@angular/core';
import { RentingReportService } from 'src/app/services/renting-report.service';
import { RentRequest } from 'src/app/models/RentRequest';

@Component({
  selector: 'app-renting-report',
  template: `
  <div class="card-header" id="addNew"> Add new </div>
  <div class="card-body">
  
      <form id="reportForm">
      <label>Select renting request:</label>
            <select class="form-control form-control-sm" [(ngModel)]="request">
              <option [ngValue]="request" *ngFor="let request of rentingRequests" (click)="selectedRequest(request)">{{request.groupId}} </option>
            </select><p></p>
          <label>Enter mileage </label>
          <input class="form-control form-control-sm" type="number" id="milage" name="mileage" [(ngModel)]="mileage"><p></p>
        <label> Input renting report information: </label>
        <input class="form-control form-control-sm" type="text area" id="report" name="report" [(ngModel)]="report"><p></p>
        <button class="btn btn-primary" (click)="submitReport()">Submit</button>
      </form>
      </div>
  `,
  styles: [
  ]
})
export class RentingReportComponent implements OnInit {

  request:RentRequest;
  mileage:string;
  report:string;
  rentingRequests:RentRequest[];

  constructor(private rentingReportService: RentingReportService) { }

  ngOnInit(): void {
    this.rentingReportService.getRentingRequests().subscribe(data => {
      this.rentingRequests = data;
    });
  }


  selectedRequest(groupId:RentRequest){
    this.request = groupId;
  }

  submitReport(){

  }

}

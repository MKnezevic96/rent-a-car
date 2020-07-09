import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { RentingReport } from '../models/RentingReport';
import { RentRequest } from '../models/RentRequest';
import { Observable, throwError } from 'rxjs';



const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    'mode': 'cors'
  })
}


@Injectable({
  providedIn: 'root'
})
export class RentingReportService {


  requestsUrl:string = 'http://localhost:8282/api/renting/requests?status=paid'
  addReportUrl:string= 'http://localhost:8282/api/renting/report'

  rentingReport:RentingReport;

  

  constructor(private http:HttpClient) { }


  getRentingRequests():Observable<RentRequest[]>{
    return this.http.get<RentRequest[]>(this.requestsUrl);
  }


  addNewRentingReport(mileage:number, report:string, rentingInstanceId:number):Observable<RentingReport>{
    this.rentingReport={mileAge:mileage, report:report, rentingInstanceId:rentingInstanceId};
    return this.http.post<RentingReport>(this.addReportUrl, this.rentingReport, httpOptions);
  }
}

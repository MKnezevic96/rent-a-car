import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders } from '@angular/common/http';
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


  requestsUrl:string = 'http://localhost:8282/api/renting/requests'

  

  constructor(private http:HttpClient) { }


  getRentingRequests():Observable<RentRequest[]>{
    return this.http.get<RentRequest[]>(this.requestsUrl);
  }
}

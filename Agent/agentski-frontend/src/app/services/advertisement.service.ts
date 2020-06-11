import { Injectable } from '@angular/core';
import { Pricing } from '../models/Pricing';
import { Observable } from 'rxjs';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Car } from '../models/Car';
import { RentRequest } from '../models/RentRequest';
const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    'mode': 'cors'
  })
}
@Injectable({
  providedIn: 'root'
})
export class AdvertisementService {
 
  pricing: Pricing;
  car:Car;
  url1:string = 'http://localhost:8282/pricing';
  url2:string = 'http://localhost:8282/addCar';
  url3:string = 'http://localhost:8282/getCars'
  url4:string = 'http://localhost:8282/rentCar'

  constructor(private http:HttpClient) { }


  addPricing(pricing: Pricing): Observable<Pricing>{
    console.log('adding pricing');
    return this.http.post<Pricing>( this.url1, pricing, httpOptions);
  }

  addCar(namePricing:string, carModel:string, fuelType:string, milage:number, nameAdvertisement:string):Observable<Car>{
    this.car={pricing:namePricing, fuelType:fuelType, carModel:carModel, milage:milage, name:nameAdvertisement, user:null };
    return this.http.post<Car>(this.url2, this.car, httpOptions);

  }

  getCar():Observable<Car[]>{
    return this.http.get<Car[]>(this.url3);
  }

  getPricing() {
    console.log('get.. pricing');

  return this.http.get<Pricing[]>(this.url1);  }

  addRentRequest(rentRequest:RentRequest):Observable<RentRequest>{
    return this.http.post<RentRequest>(this.url4, rentRequest, httpOptions);

  }
}
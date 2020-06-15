import { Injectable } from '@angular/core';
import { Pricing } from '../models/Pricing';
import { Observable } from 'rxjs';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Car } from '../models/Car';
import { RentRequest } from '../models/RentRequest';
import { CarDetails } from '../models/CarDetails';
import { CarReview } from '../models/CarReview';
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
  carReview:CarReview;
  url1:string = 'http://localhost:8282/addPricing';
  url2:string = 'http://localhost:8282/addCar';
  url3:string = 'http://localhost:8282/api/renting/cars'
  url4:string = 'http://localhost:8282/rentCar'
  getCarDetailsUrl:string = 'http://localhost:8282/api/renting/cars/'
  addCarReviewUrl:string='http://localhost:8282/api/renting/review'
  

  constructor(private http:HttpClient) { }


  addPricing(pricing: Pricing): Observable<Pricing>{
    console.log('sending');
    return this.http.post<Pricing>( this.url1, pricing, httpOptions);
  }

  addCar(namePricing:string, carModel:string, fuelType:string, milage:number, nameAdvertisement:string, startDate:Date, endDate:Date):Observable<Car>{
    this.car={pricing:namePricing, fuelType:fuelType, carModel:carModel, milage:milage, name:nameAdvertisement, startDate:startDate, endDate:endDate, id:null};
    return this.http.post<Car>(this.url2, this.car, httpOptions);

  }

  getCar():Observable<Car[]>{
    return this.http.get<Car[]>(this.url3);
  }

  addRentRequest(rentRequest:RentRequest):Observable<RentRequest>{
    return this.http.post<RentRequest>(this.url4, rentRequest, httpOptions);

  }

  getCarDetails(id:number):Observable<CarDetails>{
    return this.http.get<CarDetails>(this.getCarDetailsUrl+id);
  }

  addCarReview( carId:number, rating:number, review:string):Observable<CarReview>{
    this.carReview={id:null, reviewerId:null, carId:carId, rating:rating, approved:null, deleted:false, review:review};
    return this.http.post<CarReview>(this.addCarReviewUrl, this.carReview, httpOptions);

  }
}
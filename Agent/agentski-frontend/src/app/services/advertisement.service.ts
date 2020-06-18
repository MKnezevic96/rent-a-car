import { Injectable } from '@angular/core';
import { Pricing } from '../models/Pricing';
import { Observable } from 'rxjs';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Car } from '../models/Car';
import { RentRequest } from '../models/RentRequest';
import { UserService } from '../security/user.service';

//let token = this.localStorage.getItem("user");//this.msal.accessToken;


@Injectable({
  providedIn: 'root'
})
export class AdvertisementService {
  
  pricing: Pricing;
  car:Car;
  url1:string = 'http://localhost:8282/pricing';
  url2:string = 'http://localhost:8282/addCar';
  url3:string = 'http://localhost:8282/getCars';
  url4:string = 'http://localhost:8282/rentCar';
  
  
  constructor(private http:HttpClient, private userService: UserService) { }
  
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'mode': 'cors',
      // 'Authorization': 'Bearer ' + JSON.parse(this.localStorage.getItem('accessToken')),
    })
  }
  
  
  addPricing(pricing: Pricing): Observable<Pricing>{
    //let token = this.userService.getToken();
    let token = localStorage.getItem('accessToken');
    var httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'mode': 'cors',
        // 'Authorization': 'Bearer ' + JSON.parse(this.localStorage.getItem('accessToken')),
        'Authorization': 'Bearer ' + token, //JSON.parse(this.localStorage.getItem('accessToken')),
      })
    }
    console.log(token);
    console.log('adding pricing');
    return this.http.post<Pricing>( this.url1, pricing, httpOptions);
  }

  addCar(namePricing:string, carModel:string, fuelType:string, milage:number, nameAdvertisement:string):Observable<Car>{
    // this.car={pricing:namePricing, fuelType:fuelType, carModel:carModel, milage:milage, name:nameAdvertisement, user:null };
    return this.http.post<Car>(this.url2, this.car, this.httpOptions);

  }

  getCar():Observable<Car[]>{
    return this.http.get<Car[]>(this.url3);
  }

  getPricing() {
    console.log('get.. pricing');

  return this.http.get<Pricing[]>(this.url1);  }

  addRentRequest(rentRequest:RentRequest):Observable<RentRequest>{
    return this.http.post<RentRequest>(this.url4, rentRequest, this.httpOptions);

  }

  // getCarDetails(id:number):Observable<CarDetails>{
  //   return this.http.get<CarDetails>(this.getCarDetailsUrl+id);
  // }

  // addCarReview( carId:number, rating:number, review:string):Observable<CarReview>{
  //   this.carReview={id:null, reviewerId:null, carId:carId, rating:rating, approved:null, deleted:false, review:review};
  //   return this.http.post<CarReview>(this.addCarReviewUrl, this.carReview, httpOptions);

  // }
}
import { Injectable } from '@angular/core';
import { Pricing } from '../models/Pricing';
import { Observable, observable } from 'rxjs';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Car } from '../models/Car';
import { RentRequest } from '../models/RentRequest';
import { CarDetails } from '../models/CarDetails';
import { CarReview } from '../models/CarReview';
import { map } from 'rxjs/operators';
//import { JwtHelperService } from '@auth0/angular-jwt';
import { UserService } from '../security/user.service';
import { Review } from '../models/Review';

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


  auti:Car[];
  pricing: Pricing;
  car:Car;
  carReview:CarReview;
  d1:string;
  d2:string;
  url1:string = 'http://localhost:8282/pricing';
  url2:string = 'http://localhost:8282/addCar';
  url3:string = 'http://localhost:8282/api/renting/cars'
  url4:string = 'http://localhost:8282/api/renting/rentCar';
  getCarDetailsUrl:string = 'http://localhost:8282/api/renting/cars/'
  addCarReviewUrl:string='http://localhost:8282/api/renting/review'
  checkUrl:string= 'http://localhost:8282/api/renting/requests/'
  getCarReviewsUrl:string = 'http://localhost:8282/api/renting/reviews/cars/'
  url33:string = 'http://localhost:8282/api/renting/mycars';
  responseStatus: number;
  url5:string = 'http://localhost:8282/api/renting/payRequests';
  url55:string = 'http://localhost:8282/api/renting/rentRequests';
  url6:string = 'http://localhost:8282/api/renting/approveRentRequest';
  url7:string = 'http://localhost:8282/api/renting/rejectRentRequest';
  url8:string = 'http://localhost:8282/api/renting/availableCars/';
  url9:string = 'http://localhost:8282/api/renting/filterCars/';

  constructor(private http:HttpClient, private userService: UserService) { }



  token = this.userService.getToken();
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'mode': 'cors',
      // 'Authorization': 'Bearer ' + JSON.parse(this.localStorage.getItem('accessToken')),
      'Authorization': 'Bearer ' + this.token, //JSON.parse(this.localStorage.getItem('accessToken')),
    })
  }


  addPricing(pricing: Pricing): Observable<Pricing>{
   let token = localStorage.getItem('accessToken');     // iz browsera
    let httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'mode': 'cors',
        'Authorization': 'Bearer ' + token,
      })
    }
    return this.http.post<Pricing>( this.url1, pricing, httpOptions);
  }

  payRent(id:number):Observable<number>{
    return this.http.post<number>(this.url5, id, this.httpOptions);

  }



  addCar(namePricing:string, carModel:string, fuelType:string, milage:number, nameAdvertisement:string, town:string):Observable<Car>{
    this.car={pricing:namePricing, fuelType:fuelType, carModel:carModel, milage:milage, name:nameAdvertisement, town: town, user:null, id:0 };
    return this.http.post<Car>(this.url2, this.car, this.httpOptions);
  }

  getCar():Observable<Car[]>{
    let token = localStorage.getItem('accessToken');     // iz browsera
    let httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'mode': 'cors',
        // 'Authorization': 'Bearer ' + JSON.parse(this.localStorage.getItem('accessToken')),
        'Authorization': 'Bearer ' + token, //JSON.parse(this.localStorage.getItem('accessToken')),
      })
    }
    return this.http.get<Car[]>(this.url3, httpOptions);
  }

  getMyCars():Observable<Car[]>{
    let token = localStorage.getItem('accessToken');
    let httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'mode': 'cors',
        'Authorization': 'Bearer ' + token,
      })
    }
    return this.http.get<Car[]>(this.url33, httpOptions);
  }

  getCarReviews(id:number):Observable<Review[]>{
    return this.http.get<Review[]>(this.getCarReviewsUrl+id);
  }

  getPricing() {
    let token = localStorage.getItem('accessToken');     // iz browsera
    let httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'mode': 'cors',
        // 'Authorization': 'Bearer ' + JSON.parse(this.localStorage.getItem('accessToken')),
        'Authorization': 'Bearer ' + token, //JSON.parse(this.localStorage.getItem('accessToken')),
      })
    }

  return this.http.get<Pricing[]>(this.url1, httpOptions );  }

  addRentRequest(rentRequest:RentRequest):Observable<RentRequest>{
    let token = localStorage.getItem('accessToken');     // iz browsera
    let httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'mode': 'cors',
        // 'Authorization': 'Bearer ' + JSON.parse(this.localStorage.getItem('accessToken')),
        'Authorization': 'Bearer ' + token, //JSON.parse(this.localStorage.getItem('accessToken')),
      })
    }
    return this.http.post<RentRequest>(this.url4, rentRequest, httpOptions);

  }
  getRentRequests():Observable<RentRequest[]>{
    let token = localStorage.getItem('accessToken');     // iz browsera
    let httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'mode': 'cors',
        // 'Authorization': 'Bearer ' + JSON.parse(this.localStorage.getItem('accessToken')),
        'Authorization': 'Bearer ' + token, //JSON.parse(this.localStorage.getItem('accessToken')),
      })
    }
    return this.http.get<RentRequest[]>(this.url55, httpOptions);
  }

  approve(id: number):Observable<number>{
    return this.http.post<number>(this.url6, id, this.httpOptions);

  }

  reject(id: number):Observable<number>{

    let token = localStorage.getItem('accessToken');     // mozda ne treba token
    let httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'mode': 'cors',
        // 'Authorization': 'Bearer ' + JSON.parse(this.localStorage.getItem('accessToken')),
        'Authorization': 'Bearer ' + token, //JSON.parse(this.localStorage.getItem('accessToken')),
      })
    }
    return this.http.post<number>(this.url7, id, httpOptions);

  }

  getCarDetails(id:number):Observable<CarDetails>{
    return this.http.get<CarDetails>(this.getCarDetailsUrl+id);
  }

  addCarReview( carId:number, rating:number, review:string):Observable<CarReview>{
    this.carReview={reviewerId:null, carId:carId, rating:rating, approved:null, deleted:false, review:review, userEmail:null};

    let token = localStorage.getItem('accessToken');
    var httpOptions  = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'mode': 'cors',
        // 'Authorization': 'Bearer ' + JSON.parse(this.localStorage.getItem('accessToken')),
        'Authorization': 'Bearer ' + token, //JSON.parse(this.localStorage.getItem('accessToken')),
      })
    }

    return this.http.post<CarReview>(this.addCarReviewUrl, this.carReview, httpOptions);

  }

  getApprovedRents():Observable<RentRequest[]>{
    let token = localStorage.getItem('accessToken');
    var httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'mode': 'cors',
        'Authorization': 'Bearer ' + token,
      })
    }
    return this.http.get<RentRequest[]>(this.url5, httpOptions);
  }

  getAvailableCars(startDate:Date, endDate:Date):Observable<Car[]>{
    let token = localStorage.getItem('accessToken');     // iz browsera
    let httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'mode': 'cors',
        'Authorization': 'Bearer ' + token,
      })
    }
    this.d1 = startDate.toString();
    this.d2 = endDate.toString();
    return this.http.get<Car[]>(this.url8+this.d1+'/'+this.d2, httpOptions);
  }


  getFilteredCars(fuelType:string, transType:string, manufac:string, carClass:string, carModel:string):Observable<Car[]>{
    let token = localStorage.getItem('accessToken');     // iz browsera
    let httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'mode': 'cors',
        'Authorization': 'Bearer ' + token,
      })
    }
    // if(fuelType==''){
    //   fuelType = 'i';
    // }
    // if(transType==''){
    //   fuelType = 'i';
    // }
    // if(manufac==''){
    //   fuelType = 'i';
    // }
    // if(carClass==''){
    //   fuelType = 'i';
    // }
    // if(carModel==''){
    //   fuelType = 'i';
    // }
    console.log(this.url9+fuelType+'/'+transType+'/'+manufac+'/'+carClass+'/'+carModel);
    return this.http.get<Car[]>(this.url9+fuelType+'/'+transType+'/'+manufac+'/'+carClass+'/'+carModel, httpOptions);
  }
}

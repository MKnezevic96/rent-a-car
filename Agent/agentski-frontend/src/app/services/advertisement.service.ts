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
import { User } from '../models/User';
import { Receipt } from '../models/Receipt';

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
  city:string;
  url1:string = 'http://localhost:8282/pricing';
  url2:string = 'http://localhost:8282/addCar';
  url3:string = 'http://localhost:8282/api/renting/cars'
  url4:string = 'http://localhost:8282/api/renting/rentCar';
  url44:string = 'http://localhost:8282/api/renting/makeReservation';
  getCarDetailsUrl:string = 'http://localhost:8282/api/renting/cars/'
  addCarReviewUrl:string='http://localhost:8282/api/renting/review'
  checkUrl:string= 'http://localhost:8282/api/renting/requests/'
  getCarReviewsUrl:string = 'http://localhost:8282/api/renting/reviews/cars/'
  url33:string = 'http://localhost:8282/api/renting/mycars';
  responseStatus: number;
  url5:string = 'http://localhost:8282/api/renting/payRequests';
  url55:string = 'http://localhost:8282/api/renting/requests?status=pending';
  url6:string = 'http://localhost:8282/api/renting/approveRentRequest';
  url7:string = 'http://localhost:8282/api/renting/rejectRentRequest';
  url8:string = 'http://localhost:8282/api/renting/availableCars/'
  requestHistoryUrl:string = 'http://localhost:8282/api/renting/requests/history'
  cancelRequestUrl:string = 'http://localhost:8282/api/renting/requests/'
  getTopRatedCarsUrl:string = 'http://localhost:8282/cars/top-rated'
  getMostCommentedCarsUrl:string = 'http://localhost:8282/cars/most-commented'
  getHighestMileageCarsUrl:string = 'http://localhost:8282/cars/highest-mileage'

  filterUrl:string = 'http://localhost:8282/cars/filter?fuelType=';
  sortUrl:string = 'http://localhost:8282/cars?sort_by='
  getReceiptsUrl:string = 'http://localhost:8282/api/renting/receipts'
  payRentUrl:string = 'http://localhost:8282/api/renting/requests/'




  selectedStartDate:Date;
  selectedEndDate: Date;

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
    return this.http.put<number>(this.payRentUrl+id, this.httpOptions);

  }

  setSelectedStartDate(start:Date){
    this.selectedStartDate = start;
  }

  setSelectedEndDate(end:Date){
    this.selectedEndDate = end;
  }

  getSelectedStartDate(){
    return this.selectedStartDate;
  }

  getSelectedEndDate(){
    return this.selectedEndDate;
  }

  setAvailableCars(cars:Car[]){
    this.auti = cars;
  }

  getAvailable(){
    return this.auti;
  }

  setCity(city:string){
    this.city = city;
  }

  getCity(){
    return this.city;
  }


  addCar(namePricing:string, carModel:string, fuelType:string, milage:number, nameAdvertisement:string, town:string, slika:string):Observable<Car>{
    this.car={pricing:namePricing, fuelType:fuelType, carModel:carModel, milage:milage, name:nameAdvertisement, town: town, user:null, id:0, image:slika};
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


  getTopRatedCars():Observable<CarDetails[]>{
    let token = localStorage.getItem('accessToken');
    let httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'mode': 'cors',
        'Authorization': 'Bearer ' + token,
      })
    }
    return this.http.get<CarDetails[]>(this.getTopRatedCarsUrl, httpOptions);
  }

  getMostCommentedCars():Observable<CarDetails[]>{
    let token = localStorage.getItem('accessToken');
    let httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'mode': 'cors',
        'Authorization': 'Bearer ' + token,
      })
    }
    return this.http.get<CarDetails[]>(this.getMostCommentedCarsUrl, httpOptions);
  }

  getHighestMileageCars():Observable<CarDetails[]>{
    let token = localStorage.getItem('accessToken');
    let httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'mode': 'cors',
        'Authorization': 'Bearer ' + token,
      })
    }
    return this.http.get<CarDetails[]>(this.getHighestMileageCarsUrl, httpOptions);
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

  makeReservation(rentRequest:RentRequest):Observable<RentRequest>{
    let token = localStorage.getItem('accessToken');     // iz browsera
    let httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'mode': 'cors',
        // 'Authorization': 'Bearer ' + JSON.parse(this.localStorage.getItem('accessToken')),
        'Authorization': 'Bearer ' + token, //JSON.parse(this.localStorage.getItem('accessToken')),
      })
    }
    return this.http.post<RentRequest>(this.url44, rentRequest, httpOptions);

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

  getAvailableCars(startDate:Date, endDate:Date, town:string):Observable<Car[]>{
    let token = localStorage.getItem('accessToken');
    let httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'mode': 'cors',
        'Authorization': 'Bearer ' + token,
      })
    }
    this.d1 = startDate.toString();
    this.d2 = endDate.toString();
    return this.http.get<Car[]>(this.url8+this.d1+'/'+this.d2+'/'+town, httpOptions);
  }


  getFilteredCars(fuelType:string, transType:string, manufac:string, carClass:string, carModel:string, minPrice:string, maxPrice:string, minMileage:string, maxMileage:string, childSeats:string, mileageLimit:string, waiver:boolean):Observable<CarDetails[]>{
    let token = localStorage.getItem('accessToken');
    let httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'mode': 'cors',
        'Authorization': 'Bearer ' + token,
      })
    }

    return this.http.get<CarDetails[]>(this.filterUrl+fuelType+'&transType='+transType+'&manufac='+manufac+'&carClass='+carClass+'&carModel='+carModel+'&minPrice='
    +minPrice+'&maxPrice='+maxPrice+'&minMileage='+minMileage+'&maxMileage='+maxMileage+'&childSeats='+childSeats+'&mileageLimit='+mileageLimit+'&waiver='
    +waiver, httpOptions);
  }



  sortFilteredCars(cars:CarDetails[], type:string, entity:string):Observable<CarDetails[]>{
    let token = localStorage.getItem('accessToken');
    let httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'mode': 'cors',
        'Authorization': 'Bearer ' + token,
      })
    }
    return this.http.post<CarDetails[]>(this.sortUrl+type+'('+entity+')', cars, httpOptions);
  }

  getCurrentUser():Observable<User> {

    let token = localStorage.getItem('accessToken');
    let httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'mode': 'cors',
        'Authorization': 'Bearer ' + token,
      })
    }

    return this.http.get<User>('http://localhost:8282/user/current', httpOptions)
}


cancelRentRequest(id: number):Observable<number>{
  return this.http.post<number>(this.cancelRequestUrl+id+'/cancel', this.httpOptions);
}

getRequestHistory():Observable<RentRequest[]>{
  let token = localStorage.getItem('accessToken');
   let httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'mode': 'cors',
      'Authorization': 'Bearer ' + token,
    })
  }

  return this.http.get<RentRequest[]>(this.requestHistoryUrl, httpOptions);
}

getReceipts():Observable<Receipt[]>{
  let token = localStorage.getItem('accessToken');
   let httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'mode': 'cors',
      'Authorization': 'Bearer ' + token,
    })
  }

  return this.http.get<Receipt[]>(this.getReceiptsUrl, httpOptions);
}

}

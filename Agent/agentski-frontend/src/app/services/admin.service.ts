import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import {CarClass} from '../models/CarClass';
import {Manufacturer} from '../models/Manufacturer';
import {TransmissionType} from '../models/TransmissionType';
import {FuelType} from '../models/FuelType';
import {CarModels} from '../models/CarModels';
import { User } from '../models/User';
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
export class AdminService {
  getPricing() {
    throw new Error("Method not implemented.");
  }

  url1:string = 'http://localhost:8282/admin/addManufac';
  url2:string = 'http://localhost:8282/admin/addCarC';
  url3:string = 'http://localhost:8282/admin/addTrans';
  url14:string = 'http://localhost:8282/admin/addFuelType';

  url4:string = 'http://localhost:8282/admin/getManufacturers';
  url5:string = 'http://localhost:8282/admin/getCarClasses';
  url6:string = 'http://localhost:8282/admin/getTransmissions';
  url13:string = 'http://localhost:8282/admin/getFuelTypes';

  url7:string = 'http://localhost:8282/admin/deleteManufacturer';
  url8:string = 'http://localhost:8282/admin/deleteCarClass';
  url9:string = 'http://localhost:8282/admin/deleteTransmissionType';
  url16:string = 'http://localhost:8282/admin/deleteFuelType';

  url10:string = 'http://localhost:8282/admin/updateManufacturer';
  url11:string = 'http://localhost:8282/admin/updateCarClass';
  url12:string = 'http://localhost:8282/admin/updateTransmissionType';
  url15:string = 'http://localhost:8282/admin/updateFuelType'

  url17:string = 'http://localhost:8282/admin/addCarModel';
  url18:string = 'http://localhost:8282/admin/getCarModels';
  url19:string = 'http://localhost:8282/admin/deleteCarModel';
  url20:string = 'http://localhost:8282/admin/updateCarModel';

  url21:string = 'http://localhost:8282/admin/getUserRequests';
  url22:string = 'http://localhost:8282/admin/getUsers';
  url23:string = 'http://localhost:8282/admin/activateAcc';
  url27:string = 'http://localhost:8282/admin/ractivateAcc';

  url24:string = 'http://localhost:8282/admin/blockAcc';
  url25:string = 'http://localhost:8282/admin/getBlockedUsers';

  url26:string = 'http://localhost:8282/admin/carReviews';
  url28:string = 'http://localhost:8282/admin/deleteAcc';
  privilege:string = 'http://localHost:8282/admin/privilege/'

  carClass:CarClass;
  manufact:Manufacturer;
  transmission:TransmissionType;
  fuelType:FuelType;
  carModel:CarModels;


  constructor(private http:HttpClient) { }

  advertisementPrivilege(email:string):Observable<string>{
    return this.http.post<string>(this.privilege+'1', email, httpOptions);

  }

  rentRequestPrivilege(email:string):Observable<string>{
    return this.http.post<string>(this.privilege+'2', email, httpOptions);

  }

  activate(email:string):Observable<string>{
    return this.http.post<string>(this.url23, email, httpOptions);

  }

  block(email:string):Observable<string>{
    return this.http.post<string>(this.url24, email, httpOptions);

  }

  delete(email:string):Observable<string>{
    return this.http.post<string>(this.url28, email, httpOptions);

  }

  reactivate(email:string):Observable<string>{
    return this.http.post<string>(this.url27, email, httpOptions);
  }

  addCM(name:string, caarc:string, trrans:string, maanu:string):Observable<CarModels>{
    this.carModel={name:name, carClass:caarc, transmission:trrans, manufacturer:maanu, deleted:false};
    return this.http.post<CarModels>(this.url17, this.carModel, httpOptions);
  }

  addM(name: string): Observable<string>{
    return this.http.post<string>(this.url1, name, httpOptions);
  }

  addCC(name: string): Observable<string>{
    return this.http.post<string>(this.url2, name, httpOptions);
  }

  addTT(name: string): Observable<string>{
    return this.http.post<string>(this.url3, name, httpOptions);
  }

  approve(id:number): Observable<number>{
    return this.http.post<number>(this.url26, id, httpOptions);

  }

  addFT(name: string): Observable<string>{
    return this.http.post<string>(this.url14, name, httpOptions);
  }

  updateM(old: string, name:string, deleted:boolean): Observable<string>{
    this.manufact = {name:name, deleted:deleted}
    return this.http.post<string>(this.url10+'/'+old, this.manufact, httpOptions);
  }

  updateCC(old: string, name:string, deleted:boolean): Observable<string>{
    this.carClass = {name:name, deleted:deleted}
    return this.http.post<string>(this.url11+'/'+old, this.carClass, httpOptions);
  }

  updateTT(old: string, name:string, deleted:boolean): Observable<string>{
    this.transmission = {name:name, deleted:deleted}
    return this.http.post<string>(this.url12+'/'+old, this.transmission, httpOptions);
  }

  updateFT(old: string, name:string, deleted:boolean): Observable<string>{
    this.fuelType = {name:name, deleted:deleted}
    return this.http.post<string>(this.url15+'/'+old, this.fuelType, httpOptions);
  }

  updateCarModel(old: string, name:string, deleted:boolean): Observable<string>{
    this.carModel = {name:name, deleted:deleted,  manufacturer:'', carClass:'', transmission:''}
    return this.http.post<string>(this.url20+'/'+old, this.carModel, httpOptions);
  }

  getManufac():Observable<Manufacturer[]>{
    return this.http.get<Manufacturer[]>(this.url4);
  }

  getFuelT():Observable<FuelType[]>{
    return this.http.get<FuelType[]>(this.url13);
  }

  getUserRequests():Observable<User[]>{
    return this.http.get<User[]>(this.url21);
  }

  getUsers():Observable<User[]>{
    return this.http.get<User[]>(this.url22);
  }

  getBlockedUsers():Observable<User[]>{
    return this.http.get<User[]>(this.url25);
  }

  getTransmis():Observable<TransmissionType[]>{
    return this.http.get<TransmissionType[]>(this.url6);
  }

  getCarC():Observable<CarClass[]>{
    return this.http.get<CarClass[]>(this.url5);
  }

  getPendingComments():Observable<Review[]>{
    return this.http.get<Review[]>(this.url26);
  }

  getCarModels():Observable<CarModels[]>{
    return this.http.get<CarModels[]>(this.url18);
  }

  deleteManufacturer(name:string): Observable<string>{
    return this.http.post<string>(this.url7, name, httpOptions);
  }

  deleteCarClass(name:string): Observable<string>{
    return this.http.post<string>(this.url8, name, httpOptions);
  }

  deleteCarModel(name:string): Observable<string>{
    return this.http.post<string>(this.url19, name, httpOptions);
  }

  deleteTransmissionType(name:string): Observable<string>{
    return this.http.post<string>(this.url9, name, httpOptions);
  }

  deleteFuelType(name:string): Observable<string>{
    return this.http.post<string>(this.url16, name, httpOptions);
  }
}

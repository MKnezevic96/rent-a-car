import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import {CarClass} from '../models/CarClass';
import {Manufacturer} from '../models/Manufacturer';
import {TransmissionType} from '../models/TransmissionType';
import {FuelType} from '../models/FuelType';


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

  carClass:CarClass;
  manufact:Manufacturer;
  transmission:TransmissionType;
  fuelType:FuelType;


  constructor(private http:HttpClient) { }

  addM(name: string): Observable<string>{
    console.log('sending');
    return this.http.post<string>(this.url1, name, httpOptions);
  }

  addCC(name: string): Observable<string>{
    console.log('sending');
    return this.http.post<string>(this.url2, name, httpOptions);
  }

  addTT(name: string): Observable<string>{
    console.log('sending');
    return this.http.post<string>(this.url3, name, httpOptions);
  }

  addFT(name: string): Observable<string>{
    console.log('sending');
    return this.http.post<string>(this.url14, name, httpOptions);
  }

  updateM(old: string, name:string, deleted:boolean): Observable<string>{
    console.log('sending');
    this.manufact = {name:name, deleted:deleted}
    return this.http.post<string>(this.url10+'/'+old, this.manufact, httpOptions);
  }

  updateCC(old: string, name:string, deleted:boolean): Observable<string>{
    console.log('sending');
    this.carClass = {name:name, deleted:deleted}
    return this.http.post<string>(this.url11+'/'+old, this.carClass, httpOptions);
  }

  updateTT(old: string, name:string, deleted:boolean): Observable<string>{
    console.log('sending');
    this.transmission = {name:name, deleted:deleted}
    return this.http.post<string>(this.url12+'/'+old, this.transmission, httpOptions);
  }

  updateFT(old: string, name:string, deleted:boolean): Observable<string>{
    console.log('sending');
    this.fuelType = {name:name, deleted:deleted}
    return this.http.post<string>(this.url15+'/'+old, this.fuelType, httpOptions);
  }

  getManufac():Observable<Manufacturer[]>{
    return this.http.get<Manufacturer[]>(this.url4);
  }

  getFuelT():Observable<FuelType[]>{
    return this.http.get<FuelType[]>(this.url13);
  }

  getTransmis():Observable<TransmissionType[]>{
    return this.http.get<TransmissionType[]>(this.url6);
  }

  getCarC():Observable<CarClass[]>{
    return this.http.get<CarClass[]>(this.url5);
  }

  deleteManufacturer(name:string): Observable<string>{
    return this.http.post<string>(this.url7, name, httpOptions);
  }

  deleteCarClass(name:string): Observable<string>{
    return this.http.post<string>(this.url8, name, httpOptions);
  }

  deleteTransmissionType(name:string): Observable<string>{
    return this.http.post<string>(this.url9, name, httpOptions);
  }

  deleteFuelType(name:string): Observable<string>{
    return this.http.post<string>(this.url16, name, httpOptions);
  }
}

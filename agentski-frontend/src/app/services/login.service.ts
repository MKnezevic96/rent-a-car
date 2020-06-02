import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import {LoginUser} from '../models/LoginUser'

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    'mode': 'cors'
  })
}

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  
  loginUser:LoginUser;
  url:string = 'http://localhost:8282/api/login';

  constructor(private http:HttpClient) { }

  onLogin(loginUser: LoginUser): Observable<LoginUser>{
    console.log('sending');
    return this.http.post<LoginUser>(this.url, loginUser, httpOptions);
    

  }
}

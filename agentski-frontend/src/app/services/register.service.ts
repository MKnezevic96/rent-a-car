import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import {User} from '../models/User'
import { UserRequest } from '../models/UserRequest';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    'mode': 'cors'
  })
}

@Injectable({
  providedIn: 'root'
})
export class RegisterService {
  
  user:UserRequest;
  url:string = 'http://localhost:8282/api/register';

  constructor(private http:HttpClient) { }

  onRegister(user: UserRequest): Observable<UserRequest>{
    console.log('sending');
    console.log(user);
    return this.http.post<UserRequest>(this.url, user, httpOptions);
    

  }
}

import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {Router} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';
import { UserTokenState } from '../models/UserTokenState';
import { LoginUser } from '../models/LoginUser';
import { HttpHeaders } from '@angular/common/http';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    'mode': 'cors',
  })
}


@Injectable({
  providedIn: 'root'
})
export class UserService {

  currentUser;
  accessToken;
  role = null;
  request: UserTokenState;
  loggedInUser: Observable<UserTokenState>;
  loggedInUserSubject: BehaviorSubject<UserTokenState>;

  constructor(private router: Router, private httpClient: HttpClient) {
    this.loggedInUserSubject = new BehaviorSubject<UserTokenState>(JSON.parse(localStorage.getItem('user')));
    this.loggedInUser = this.loggedInUserSubject.asObservable();
  }

  login(loginRequest: LoginUser) {
    return this.httpClient.post('http://localhost:8083/authentication/login', loginRequest).pipe(map((response: UserTokenState) => {
      this.accessToken = response.accessToken;
      this.role = response.role;
      localStorage.setItem('user', JSON.stringify(response));
      localStorage.setItem('role', this.role);
      localStorage.setItem('accessToken', response.accessToken);
      this.loggedInUserSubject.next(response);

      if(this.role=="admin") {
        this.router.navigateByUrl('adminPage');
      } else if (this.role=="user") {
        this.router.navigateByUrl('index');
      }

    }));
  }

  checkPassword(oldPassword:string):Observable<string>{
    let token = localStorage.getItem('accessToken');     // iz browsera
    let httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'mode': 'cors',
        'Authorization': 'Bearer ' + token,
      })
    }
    let url = 'http://localhost:8083/authentication/checkPassword';
    return this.httpClient.post<string>(url, oldPassword, httpOptions);

  }

  changePassword(newPassword:string):Observable<string>{
    let token = localStorage.getItem('accessToken');     // iz browsera
    let httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'mode': 'cors',
        'Authorization': 'Bearer ' + token,
      })
    }
    let url = 'http://localhost:8083/authentication/changePassword';
    return this.httpClient.post<string>(url, newPassword, httpOptions);

  }

  getMyInfo() {
    return this.httpClient.get('https://localhost:8443/authentication-service/api/users/whoami').subscribe(data => {
      this.currentUser = data;
    });
  }

  getToken() {
    return this.accessToken;
  }

  getLoggedInUser() {
    return this.loggedInUserSubject.value;
  }

  getRole() {
    return this.role;
  }

  isLoggedIn() {
    return localStorage.getItem('user') != null;
  }

  logout() {
    localStorage.removeItem('user');
    localStorage.removeItem('role');
    localStorage.removeItem('accessToken');
    localStorage.removeItem('optc');
    localStorage.removeItem('test');

    this.accessToken = null;
    this.router.navigate(['/']);
    let url = 'http://localhost:8083/authentication/izadji';
    return this.httpClient.get<string>(url, httpOptions);
    
  }

  recoverEmail(email:string):Observable<string>{
    let httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'mode': 'cors',
      })
    }
    let url = 'http://localhost:8083/authentication/recoverEmail';
    return this.httpClient.post<string>(url, email, httpOptions);
  }

}
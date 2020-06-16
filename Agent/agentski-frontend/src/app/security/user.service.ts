import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {Router} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';
import { UserTokenState } from '../models/UserTokenState';
import { LoginUser } from '../models/LoginUser';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  currentUser;
  accessToken = null;
  role = null;
  request: UserTokenState;
  loggedInUser: Observable<UserTokenState>;
  loggedInUserSubject: BehaviorSubject<UserTokenState>;

  constructor(private router: Router, private httpClient: HttpClient) {
    this.loggedInUserSubject = new BehaviorSubject<UserTokenState>(JSON.parse(localStorage.getItem('user')));
    this.loggedInUser = this.loggedInUserSubject.asObservable();
  }

  login(loginRequest: LoginUser) {
    return this.httpClient.post('http://localhost:8282/login', loginRequest).pipe(map((response: UserTokenState) => {
      console.log(response.role);
      this.accessToken = response.accessToken;
      this.role = response.role;
      localStorage.setItem('user', JSON.stringify(response));
      localStorage.setItem('role', this.role);
      this.loggedInUserSubject.next(response);
    }));
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
    this.accessToken = null;
    this.router.navigate(['/']);
  }

}
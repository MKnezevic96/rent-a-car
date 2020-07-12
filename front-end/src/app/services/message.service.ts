import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../models/User';
import { Message } from '../models/Message';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { UserService } from '../security/user.service';

@Injectable({
  providedIn: 'root'
})
export class MessageService {
  message:Message;

  sendMessageUrl:string= 'http://localhost:8083/renting/message'
  getUsersUrl:string= 'http://localhost:8083/renting/users'
  getMessageHistoryUrl:string = 'http://localhost:8083/renting/history'

  constructor(private http:HttpClient, private userService:UserService ) { }

  token = this.userService.getToken();
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'mode': 'cors',
      // 'Authorization': 'Bearer ' + JSON.parse(this.localStorage.getItem('accessToken')),
    })
  }



  getUsersList():Observable<User[]>{

    let token = localStorage.getItem('accessToken');
    var httpOptions  = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'mode': 'cors',
        // 'Authorization': 'Bearer ' + JSON.parse(this.localStorage.getItem('accessToken')),
        'Authorization': 'Bearer ' + token, //JSON.parse(this.localStorage.getItem('accessToken')),
      })
    }
    return this.http.get<User[]>(this.getUsersUrl, httpOptions);
  }

  getMessageHistory(email:string):Observable<Message[]>{

    let token = localStorage.getItem('accessToken');
    var httpOptions  = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'mode': 'cors',
        // 'Authorization': 'Bearer ' + JSON.parse(this.localStorage.getItem('accessToken')),
        'Authorization': 'Bearer ' + token, //JSON.parse(this.localStorage.getItem('accessToken')),
      })
    }
    return this.http.get<Message[]>(this.getMessageHistoryUrl+'?email='+email, httpOptions);
  }


  sendMessage(content:string, userTo:string):Observable<Message>{
    this.message={content:content, userFromEmail:null, userToEmail:userTo, date:null, id:null};

    let token = localStorage.getItem('accessToken');
    var httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'mode': 'cors',
        // 'Authorization': 'Bearer ' + JSON.parse(this.localStorage.getItem('accessToken')),
        'Authorization': 'Bearer ' + token, //JSON.parse(this.localStorage.getItem('accessToken')),
      })
    }

    return this.http.post<Message>(this.sendMessageUrl, this.message, httpOptions);
  }

}

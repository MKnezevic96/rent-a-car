import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/User';
import { Message } from 'src/app/models/Message';
import { MessageService } from 'src/app/services/message.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { first } from 'rxjs/operators';
import { UserService } from 'src/app/security/user.service';

@Component({
  selector: 'app-messages',
  template: `

  <nav class="navbar navbar-expand-lg navbar-light bg-light">
  <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav">
          <li class="nav-item active">
              <a class="nav-link">Home <span class="sr-only">(current)</span></a>
          </li>
      </ul>
  </div>
  <button class="btn btn-primary" style="margin-top: 0 !important;" (click)="logout()">Log out</button>
</nav>

<div class="container fluid">
 

  <ng-container *ngIf="msglist">
  <table class="table">
       <thead>
         <tr>           
           <th>First Name</th>
           <th>Last name</th>
           <th>Email</th>
         </tr>
       </thead>
       <tbody>
         <tr *ngFor="let user of users">           
           <td>{{ user.firstname }}</td>
           <td>{{ user.lastname }}</td>
           <td>{{ user.email }}</td>
           
           <td><button class="btn btn-primary" (click)="showHistory(user.email)">Show history</button></td>
          
       </tr>
       </tbody>
     </table>
</ng-container>

<ng-container *ngIf="history">
  <table class="table">
       <thead>
         <tr>           
           
         </tr>
       </thead>
       <tbody>
         <tr *ngFor="let msg of messages">           
           <td>From: {{ msg.userFromEmail }}</td>
           <td>{{ msg.content }}</td>
           <td>Date: {{ msg.date }}</td>
       </tr>
       </tbody>
     </table>


<div class="card-body">

    <div id="addMsg">
        <textarea class="form-control form-control-sm"  id="content" name="content" [(ngModel)]="content" rows="1"></textarea><p></p>
      <button class="btn btn-primary" (click)="sendMessage()">Send</button>
    </div>
    </div>


</ng-container>
  `,
  styles: [
  ]
})
export class MessagesComponent implements OnInit {


  users:User[]
  messages:Message[];
  history:boolean = false;
  msglist:boolean = true;
  userTo:string;
  content:string;
  

  constructor(private messageService: MessageService, private route: ActivatedRoute, private router: Router, private http:HttpClient, private userService:UserService) { }

  ngOnInit(): void {
    this.messageService.getUsersList().subscribe(data =>{
      this.users = data;
    });
  }

  showHistory(email:string){
    this.msglist = false;
    this.history = true;
    this.userTo = email;

    this.messageService.getMessageHistory(email).subscribe(data =>{
      this.messages = data;
    })
    
  }


  sendMessage(){
    this.messageService.sendMessage(this.content,this.userTo).pipe(first())
    .subscribe(
        data => {
          this.messageService.getMessageHistory(this.userTo).subscribe(data =>{
            this.messages = data;
            this.content="";
          }, error=>{
            if(error.status == 403){
              alert('This action has been blocked by admin');
            }
          })
        })
  }
  
  logout(){
    this.userService.logout().subscribe(data =>{
    });
  }
}

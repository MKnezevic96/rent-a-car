import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';
import { UserService } from 'src/app/security/user.service';

@Component({
  selector: 'app-password-change',
  templateUrl: './password-change.component.html',
  styleUrls: ['./password-change.component.css']
})
export class PasswordChangeComponent implements OnInit {

  oldPassword:string;
  newPassword:string;
  checked:boolean = false;

  constructor(
    private route: ActivatedRoute,
        private router: Router,
        private loginService:LoginService,
        private userService:UserService
  ) { }

  ngOnInit(): void {
  }

  checkPassword(){
    this.userService.checkPassword(this.oldPassword).subscribe((data)=>{
      this.checked = true;
    },
    error =>{
        alert('Incorrect password');
    });
  }

  changePassword(){
    this.userService.changePassword(this.newPassword).subscribe((data)=>{
      this.router.navigateByUrl('adminPage');
    })
  }

}

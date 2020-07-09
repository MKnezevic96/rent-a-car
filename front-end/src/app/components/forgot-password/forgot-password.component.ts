import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';
import { UserService } from 'src/app/security/user.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {

  email:string;

  constructor(private route: ActivatedRoute,
        private router: Router,
        private loginService:LoginService,
        private userService:UserService) { }

  ngOnInit(): void {
  }

  onSubmit(){
    this.userService.recoverEmail(this.email).subscribe((data)=>{
      console.log('Recovery email sent');
      this.router.navigateByUrl('');

    },error =>{
      alert('Invalid email');
  });
  }
}

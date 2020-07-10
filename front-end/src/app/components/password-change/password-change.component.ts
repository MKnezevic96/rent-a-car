import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';
import { UserService } from 'src/app/security/user.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-password-change',
  templateUrl: './password-change.component.html',
  styleUrls: ['./password-change.component.css']
})
export class PasswordChangeComponent implements OnInit {

  oldPassword:string;
  newPassword:string;
  checked:boolean = false;
  userData: FormGroup;

  constructor(
    private route: ActivatedRoute,
        private router: Router,
        private loginService:LoginService,
        private userService:UserService,
        private formBuilder: FormBuilder
  ) { }

  ngOnInit(): void {
    this.userData = this.formBuilder.group({

      password: ['', [Validators.required, Validators.minLength(10), Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{10,}$/)]],
      passwordRepeat: ['', [Validators.required, Validators.minLength(10)]],


    },
    {validator: this.checkPasswords});
  }

  checkPasswords(group: FormGroup) {
    if (!group.controls.password.touched) {
      return null;
    }
    const pass = group.controls.password.value;
    const confirmPass = group.controls.passwordRepeat.value;
    return pass === confirmPass ? null : {notSame: true};
  }

  get f() {
    return this.userData.controls;
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
      this.userService.changePassword(this.userData.value.password).subscribe((data)=>{
        this.router.navigateByUrl('index');
      })
    }

}

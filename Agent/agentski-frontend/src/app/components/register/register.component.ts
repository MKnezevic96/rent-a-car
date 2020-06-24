import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/User';
import { RegisterService } from '../../services/register.service';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { first } from 'rxjs/operators';
import { AlertService } from 'src/app/services/alert.service';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { UserRequest } from '../../models/UserRequest';
import { UserService } from 'src/app/services/UserService';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
 
    userData: FormGroup;
 // notifier: NotifierService;
  userRequest: UserRequest;

  constructor( private registrationService: RegisterService,
              private formBuilder: FormBuilder, private router: Router) {
    //this.notifier = notifierService;
  }

  ngOnInit(): void {
    this.userData = this.formBuilder.group({
        
        email: ['', [Validators.required, this.emailDomainValidator, Validators.pattern(/[^ @]*@[^ @]*/)]],
        password: ['', [Validators.required, Validators.minLength(10)]],
        passwordRepeat: ['', [Validators.required, Validators.minLength(10)]],
        firstname: ['', [Validators.required, Validators.pattern(/^[a-zA-Z\s]*$/)]],
        lastname: ['', [Validators.required, Validators.pattern(/^[a-zA-Z\s]*$/)]],
        name: ['', [Validators.required, Validators.pattern(/^[a-zA-Z\s]*$/)]],
        adress: ['', [Validators.required, Validators.pattern(/^[a-zA-Z\s]*$/)]],
        number: ['', [Validators.required, Validators.pattern(/^[0-9]*$/), Validators.minLength(9), Validators.maxLength(10)]]
        
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

  emailDomainValidator(control: FormControl) {
    const email = control.value;
    const [name, domain] = email.split('@');
    if (domain !== 'gmail.com' && domain !== 'yahoo.com' && domain !== 'uns.ac.rs') {
      return {
        emailDomain: {
          parsedDomain: domain
        }
      };
    } else {
      return null;
    }
  }

  get f() {
    return this.userData.controls;
  }

 

  register() {

    const registration = new UserRequest( this.userData.value.firstname,
      this.userData.value.lastname, this.userData.value.email, this.userData.value.password, this.userData.value.name, 
      this.userData.value.adress, this.userData.value.number);

    this.registrationService.onRegister(registration).subscribe(data => {
      this.router.navigate(['/login'])
      //this.showNotification('success', 'You successfully sent a registration request.');
    });
  }
   
}

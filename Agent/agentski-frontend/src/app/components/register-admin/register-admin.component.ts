import { Component, OnInit } from '@angular/core';
import { RegisterService } from '../../services/register.service';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { UserRequest } from '../../models/UserRequest';
import { UserService } from 'src/app/security/user.service';

@Component({
  selector: 'app-register-admin',
  templateUrl: './register-admin.component.html',
  styleUrls: ['./register-admin.component.css']
})
export class RegisterAdminComponent implements OnInit {

  userData: FormGroup;
  // notifier: NotifierService;
   userRequest: UserRequest;
 
   constructor( private registrationService: RegisterService,
               private formBuilder: FormBuilder, private router: Router, private userService:UserService) {
     //this.notifier = notifierService;
   }
 
   ngOnInit(): void {
     this.userData = this.formBuilder.group({
         
         email: ['', [Validators.required, this.emailDomainValidator, Validators.pattern(/[^ @]*@[^ @]*/)]],
         password: ['', [Validators.required, Validators.minLength(10), Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{10,}$/)]],
         passwordRepeat: ['', [Validators.required, Validators.minLength(10)]],
         firstname: ['', [Validators.required, Validators.pattern(/^[a-zA-Z\s]*$/)]],
         lastname: ['', [Validators.required, Validators.pattern(/^[a-zA-Z\s]*$/)]],
         name: ['', [Validators.required, Validators.pattern(/^[a-zA-Z\s]*$/)]],
         adress: ['', [Validators.required, Validators.pattern(/^[a-zA-Z\s]*$/)]],
         number: ['', [Validators.required, Validators.pattern(/^[0-9]*$/), Validators.minLength(9), Validators.maxLength(10)]],
         pib: ['', [Validators.required, Validators.pattern(/^[0-9]*$/), Validators.minLength(8), Validators.maxLength(8)]]

         
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
       this.userData.value.lastname, this.userData.value.email, this.userData.value.password,
       'isCompany', this.userData.value.name, 
       this.userData.value.adress, this.userData.value.number, this.userData.value.pib);
 
     this.registrationService.onRegister(registration).subscribe(data => {
       this.router.navigate(['/adminPage'])
       //this.showNotification('success', 'You successfully sent a registration request.');
     });
   }

   logout(){
    this.userService.logout().subscribe(data =>{
    });
  }

}

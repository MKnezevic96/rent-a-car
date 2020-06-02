import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/User';
import { RegisterService } from '../../services/register.service';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { first } from 'rxjs/operators';
import { AlertService } from 'src/app/services/alert.service';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { UserRequest } from 'src/app/models/UserRequest';
import { UserService } from 'src/app/services/UserService';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
 
    registerForm: FormGroup;
    loading = false;
    submitted = false;

    firstname: string;
    lastname: string;
    email:string;
    password:string;
  
    isSelected: string;
    user:UserRequest;

    constructor(
        private formBuilder: FormBuilder,
        private router: Router,
        private authenticationService: AuthenticationService,
        private userService: UserService,
        private alertService: AlertService,
        private registerService: RegisterService
    ) {
        // redirect to home if already logged in
      //  if (this.authenticationService.currentUserValue) {
          //  this.router.navigate(['/']);
      //  }
    }

    ngOnInit() {
        // this.registerForm = this.formBuilder.group({
            
        //     email: ['', Validators.required],
        //     password: ['', [Validators.required, Validators.minLength(6)]]
        // });
    }

    // convenience getter for easy access to form fields
    get f() { return this.registerForm.controls; }

    onSubmit() {
        //this.submitted = true;

        // reset alerts on submit
      //  this.alertService.clear();

        // stop here if form is invalid
        // if (this.registerForm.invalid) {
        //     return;
        // }

          

            
        

        this.user={firstname: this.firstname, lastname:this.lastname, email:this.email, password:this.password, isSelected: this.isSelected};
        
        console.log(this.user.firstname);
        console.log(this.user.lastname);
        console.log(this.user.email);
        console.log(this.user.password);
        console.log(this.user.isSelected);
        
        this.loading = true;

        this.registerService.onRegister(this.user)
            .pipe(first())
            .subscribe(
                data => {
                    this.alertService.success('Registration successful', true);
                    this.router.navigate(['/login']);
                },
                error => {
                    alert("Invalid email");
                    this.alertService.error(error);
                    this.loading = false;
                });
    }
   
}

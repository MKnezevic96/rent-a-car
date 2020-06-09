import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http'
import {FormsModule} from '@angular/forms';
// import { MaterialModule, MatDatepickerModule, MatNativeDateModule } from '@angular/material';
//import { JwtModule } from '@auth0/angular-jwt'
//import { HttpModule } from '@angular/http'
import { ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule, RoutingComponents } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { AlertComponent } from './components/alert.component/alert.component';
import { HomeComponent } from './components/home/home.component';

import { AdminPageComponent } from './components/admin-page/admin-page.component';
import { AdvertisementComponent } from './components/advertisement/advertisement.component';
import { RentRequestComponent } from './components/rent-request/rent-request.component';
//import { AuthGuard } from './helpers/auth.guard';

//bootstrap
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { TooltipModule } from 'ngx-bootstrap/tooltip';
import { ModalModule } from 'ngx-bootstrap/modal';
import { UsersListComponent } from './components/users-list/users-list.component';
import { CodebookMenagementComponent } from './components/codebook-menagement/codebook-menagement.component';
import { UserMenagementComponent } from './components/user-menagement/user-menagement.component';
import { BlockedUsersListComponent } from './components/blocked-users-list/blocked-users-list.component';
import { RegistrationRequestsComponent } from './components/registration-requests/registration-requests.component';
import { CarModelsComponent } from './components/car-models/car-models.component';
import { CarClassesComponent } from './components/car-classes/car-classes.component';
import { ManufacturesComponent } from './components/manufactures/manufactures.component';
import { FuelTypesComponent } from './components/fuel-types/fuel-types.component';
import { TransmissionTypesComponent } from './components/transmission-types/transmission-types.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent, 
    RegisterComponent,
    AlertComponent, 
    HomeComponent,
    AdminPageComponent,
    AdvertisementComponent,
    RentRequestComponent,
    RoutingComponents,
    CodebookMenagementComponent,
    UserMenagementComponent,
    UsersListComponent,
    BlockedUsersListComponent,
    RegistrationRequestsComponent,
    CarModelsComponent,
    CarClassesComponent,
    ManufacturesComponent,
    FuelTypesComponent,
    TransmissionTypesComponent,
   // AuthGuard
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule, 
    ReactiveFormsModule,
    BsDropdownModule.forRoot(),
    TooltipModule.forRoot(),
    ModalModule.forRoot()
    // HttpModule,
    // JwtModule.forRoot({
    //   config: {
    //     authScheme: 'Bearer',
    //     headerName: 'Authorization',
    //     tokenGetter: () => {
    //       return localStorage.getItem('access_token');
    //     },
    //   }
    // }),
    // MaterialModule,            // <----- this module will be deprecated in the future version.
    // MatDatepickerModule,        // <----- import(must)
    // MatNativeDateModule,        // <----- import for date formating(optional)
    //MatMomentDateModule   
    
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {  }

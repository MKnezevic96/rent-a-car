import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http'
import {FormsModule} from '@angular/forms';
// import { MaterialModule, MatDatepickerModule, MatNativeDateModule } from '@angular/material';
//import { JwtModule } from '@auth0/angular-jwt';
//import { HttpModule } from '@angular/http';
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
import { CommentMenagementComponent } from './components/comment-menagement/comment-menagement.component';
import { PendingCommentsComponent } from './components/pending-comments/pending-comments.component';
import { PricingPageComponent } from './components/pricing-page/pricing-page.component';
import { RentingApproveComponent } from './components/renting-approve/renting-approve.component';

import { RentingReportComponent } from './components/renting-report/renting-report.component';
import { IndexComponent } from './components/index/index.component';
import { AdvertisementListComponent } from './components/advertisement-list/advertisement-list.component';
import { RentingMenagementComponent } from './components/renting-menagement/renting-menagement.component';
import { RentPaymentComponent } from './components/rent-payment/rent-payment.component';
import { MyCarsComponent } from './components/my-cars/my-cars.component';
import { MessagesComponent } from './components/messages/messages.component';
import { PasswordChangeComponent } from './components/password-change/password-change.component';
import { TokenInterceptor } from './security/tokenInterceptor';
import { ActivateComponent } from './components/activate/activate.component';
import { ForgotPasswordComponent } from './components/forgot-password/forgot-password.component';
import { RegisterAdminComponent } from './components/register-admin/register-admin.component';
import { FilterCarsComponent } from './components/filter-cars/filter-cars.component';


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
    RentingReportComponent,
    IndexComponent,
    AdvertisementListComponent,
    CommentMenagementComponent,
    PendingCommentsComponent,
    PricingPageComponent,
    RentingApproveComponent,
    RentingMenagementComponent,
    RentPaymentComponent,
    MyCarsComponent,
    MessagesComponent,
    PasswordChangeComponent,
    ActivateComponent,
    ForgotPasswordComponent,
    RegisterAdminComponent,
    FilterCarsComponent,
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
    ModalModule.forRoot(),

    //MatMomentDateModule

  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true}
  ],

  bootstrap: [AppComponent]
})
export class AppModule {  }

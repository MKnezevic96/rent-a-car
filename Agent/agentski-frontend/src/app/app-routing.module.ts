import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RegisterComponent } from './components/register/register.component';
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';
import { AdminPageComponent } from './components/admin-page/admin-page.component';
import { AdvertisementComponent } from './components/advertisement/advertisement.component';
import { RentRequestComponent } from './components/rent-request/rent-request.component';
import { CodebookMenagementComponent } from './components/codebook-menagement/codebook-menagement.component';
import { UserMenagementComponent } from './components/user-menagement/user-menagement.component';
import { BlockedUsersListComponent } from './components/blocked-users-list/blocked-users-list.component';
import { UsersListComponent } from './components/users-list/users-list.component';
import { RegistrationRequestsComponent } from './components/registration-requests/registration-requests.component';
import { CarModelsComponent } from './components/car-models/car-models.component';
import { ManufacturesComponent } from './components/manufactures/manufactures.component';
import { FuelTypesComponent } from './components/fuel-types/fuel-types.component';
import { CarClassesComponent } from './components/car-classes/car-classes.component';
import { TransmissionTypesComponent } from './components/transmission-types/transmission-types.component';
import { CommentMenagementComponent } from './components/comment-menagement/comment-menagement.component';
import { PendingCommentsComponent } from './components/pending-comments/pending-comments.component';
import { PricingPageComponent } from './components/pricing-page/pricing-page.component';
import { RentingApproveComponent } from './components/renting-approve/renting-approve.component';


const routes: Routes = [
  { path: '', component: LoginComponent },
    { path: 'home', component: HomeComponent },
    { path: 'register', component: RegisterComponent },
    { 
      path: 'adminPage', 
      component: AdminPageComponent,
      children: [
        { path: 'codebookMenagement', 
          component: CodebookMenagementComponent,
          children: [
            {path: 'carModels', component: CarModelsComponent},
            {path: 'carClasses', component: CarClassesComponent},
            {path: 'manufactures', component: ManufacturesComponent },
            {path: 'fuelTypes', component: FuelTypesComponent },
            {path: 'transmissionTypes', component: TransmissionTypesComponent}
          ]  
        },
        { path: 'userMenagement',
          component: UserMenagementComponent,
          children: [
            {path: 'blockedUsers', component: BlockedUsersListComponent},
            {path: 'users', component: UsersListComponent},
            {path: 'requests', component: RegistrationRequestsComponent}
          ]
        },
        { path: 'commentsMenagement',
          component: CommentMenagementComponent,
          children: [
            {path: 'pendingComments', component: PendingCommentsComponent},
          ]
        },
      ]
    },
    { path: 'advertisement', component: AdvertisementComponent},
    { path: 'rentRequest', component: RentRequestComponent},
    { path: 'pricing', component: PricingPageComponent},
    { path: 'rentingApprove', component: RentingApproveComponent},
    // otherwise redirect to home
    { path: '**', redirectTo: '' }

];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
export const RoutingComponents = [CodebookMenagementComponent, UserMenagementComponent, UsersListComponent, BlockedUsersListComponent, RegistrationRequestsComponent]

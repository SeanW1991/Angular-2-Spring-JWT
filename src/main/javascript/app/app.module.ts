import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";

import {AppComponent} from "./component/app/app.component";
import {NotFoundComponent} from "./component/404/404.component";
import {ProfileComponent} from "./component/profile/profile.component";
import {DashboardComponent} from "./component/dashboard/dashboard.component";


import {FormsModule} from "@angular/forms";
import {RouterModule, Routes} from "@angular/router";
import {HttpModule, JsonpModule} from "@angular/http";
import {LoginComponent} from "./component/login/login.component";
import {AlertComponent} from "./component/alert/alert.component";
import {AlertService} from "./service/alert.service";
import {AuthenticationService} from "./service/authentication";
import {AuthGuard} from "./guard/AuthGuard";

const appRoutes: Routes = [
    {path: '',          redirectTo: '/dashboard', pathMatch: 'full'},
    {path: 'profile',    component: ProfileComponent,    data: {title: 'Profile'}},
    {path: '404',       component: NotFoundComponent,  data: {title: 'Not Found'}},
    {path: 'dashboard', component: DashboardComponent, data: {title: 'Dash'}},
    {path: 'login',     component: LoginComponent, data: {title: 'Login'}},
    {path: '**',        redirectTo: '/404'}
];

@NgModule({
    imports: [
        BrowserModule,
        FormsModule,
        HttpModule,
        JsonpModule,
        RouterModule.forRoot(appRoutes)
    ],
    declarations: [
        AppComponent,
        AlertComponent,
        NotFoundComponent,
        DashboardComponent,
        ProfileComponent,
        LoginComponent,
    ],
    providers: [
        AlertService,
        AuthGuard,
        AuthenticationService,
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
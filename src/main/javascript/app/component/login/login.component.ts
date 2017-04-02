import {Component} from "@angular/core";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../service/authentication";
import {AuthenticationResponse} from "../../model/login_response";
import {AlertService} from "../../service/alert.service";
import {ErrorMessage} from "../../model/error_response";

@Component({
    selector: 'login',
    templateUrl: 'app/component/login/login.component.html',
    providers:[AuthenticationService]
})
export class LoginComponent {

    /**
     * The constructor containing the router, authentication service and alert service.
     * @param router The router to allow for navigation.
     * @param loginService The login service.
     * @param alertService The alert service.
     */
    constructor(private router: Router, private loginService: AuthenticationService, private alertService: AlertService) {

    }

    /**
     * Attempts to authenticate a user based on their login credentials.
     * @param event The login event.
     * @param username The attempted username.
     * @param password The attemped password.
     */
    login(event: any, username, password) {
        event.preventDefault();
        this.loginService.login(username, password)
            .subscribe((loginResponse: AuthenticationResponse) => {
                this.router.navigate(['/profile']);
            }, (error) => {
                let errorObject: ErrorMessage = error.json();
                this.alertService.error(errorObject.message);
                console.log(errorObject.message);
            });
    }

    /**
     * Removes the local storage session of the logged in user.
     */
    logout(): void {
        localStorage.removeItem('jwt');
    }
}
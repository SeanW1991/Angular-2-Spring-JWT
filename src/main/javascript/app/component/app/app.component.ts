import {Component} from "@angular/core";
import {AuthenticationService} from "../../service/authentication";
import {Router} from "@angular/router";

@Component({
    selector: 'app',
    templateUrl: 'app/component/app/app.component.html',
    styleUrls: ['app/component/app/app.component.css']
})
export class AppComponent {

    /**
     * The constructor used to reference the router and the authentication service.
     * @param router The router to allow for navigation through the app.
     * @param authService The authentication service for the checking if a user is signed in
     * and to log the user out.
     */
    constructor(private router:Router, private authService : AuthenticationService) {

    }

    /**
     * Checks to see if the user is signed in.
     * @returns {boolean} True if the user is signed in, false if not.
     */
    isSignedIn() : boolean {
        return this.authService.isSignedIn();
    }

    /**
     * Logouts the user by removing their local session key
     * and navigated them back to the login screen.
     */
    logout() : void {
        this.authService.remove();
        this.router.navigate(['/login']);
    }
}
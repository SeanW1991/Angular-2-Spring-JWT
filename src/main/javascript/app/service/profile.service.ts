import {Injectable} from "@angular/core";
import {Headers, Http, RequestOptions, Response} from "@angular/http";
import {Observable} from "rxjs/Observable";
import "rxjs/Rx";
import {AuthenticationService} from "./authentication";
import {AuthenticationResponse} from "../model/login_response";
import {Profile} from "../model/profile";
import {JSONService} from "./json.service";

@Injectable()
export class ProfileService extends JSONService {

    /**
     * The url error the token refresh api.
     */
    private static PROFILE_API_URL : string = '/api/me';

    constructor(public http: Http, public loginService : AuthenticationService) {
        super(http, loginService);
    }

    /**
     * Retreives the authorised logged in user information from the restful endpoint.
     */
    getLoggedInProfile(): Observable<Profile> {
        let observable : Observable<Profile> = this.getAuthorised(ProfileService.PROFILE_API_URL);
        return observable;
    }
}
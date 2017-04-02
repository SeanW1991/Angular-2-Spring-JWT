import {Observable} from "rxjs/Observable";
import {Injectable} from "@angular/core";
import {Headers, Http, RequestOptions, Response} from "@angular/http";
import "rxjs/Rx";
import {AuthenticationResponse} from "../model/login_response";

@Injectable()
export class AuthenticationService {

    /**
     * The url error the token refresh api.
     */
    private static REFRESH_TOKEN_URL : string = '/api/token/refresh';

    /**
     * Authentication headers.
     * @type {Headers}, The headers for the authentication post.
     */
    static headers = new Headers({'Content-Type': 'application/json', 'Accept': 'application/json'});

    /**
     * Constructor containing the {@link Http).
     * @param http The {@link Http} reference.
     */
    constructor(private http:Http) {}

    /**
     * Requests a login operation to occur to the restful api interface.
     * @param username The username create the user.
     * @param password The password create the user.
     * @returns {Observable<AuthenticationResponse>} Returns an {@link Observable} with the {@link AuthenticationResponse}.
     */
    login(username, password) : Observable<AuthenticationResponse> {

        /**
         * Turns the username and password into a json string.
         */
        let loginRequest = JSON.stringify({username: username, password: password});

        /**
         * Posts a login request to the restful api.
         */
        return this.http.post('/api/auth/login', loginRequest, { headers: AuthenticationService.headers })
            .mergeMap((response: Response) => {
                let loginResponse: AuthenticationResponse = response.json();
                if(loginResponse.refreshToken.length < 1 && loginResponse.token.length < 1) {
                    return Observable.throw("Error, login response invalid.");
                }
                return Observable.of(loginResponse);

                /**
                 * Turns the login response into a json string and stores it into the local storage.
                 */
            }).map((loginResponse : AuthenticationResponse) => {
                localStorage.setItem('jwt', JSON.stringify(loginResponse));
                return loginResponse;
            }
        );
    }

    /**
     * Updates the access token.
     * @param value The new token value.
     */
    updateAccessToken(value : string) : void {
        let response: AuthenticationResponse = this.decodeLoginResponseFromStorage();
        response.token = value;
        localStorage.setItem('jwt', JSON.stringify(response));
    }

    /**
     * Decodes the json string within the local storage to a {@link AuthenticationResponse}.
     */
    decodeLoginResponseFromStorage() : AuthenticationResponse  {
        let response: AuthenticationResponse = JSON.parse(localStorage.getItem('jwt'));
        return response;
    }

    /**
     * Validates the AuthenticationResponse as an Observable.
     * @returns {Observable<AuthenticationResponse>}
     */
    toAuthenticationResponseObservable() : Observable<AuthenticationResponse> {
        let responseObservable : Observable<AuthenticationResponse>  = Observable.of(this.decodeLoginResponseFromStorage())
            .mergeMap((loginResponse : AuthenticationResponse) => {
                if(loginResponse == null) {
                    return Observable.throw("Error, Unauthorized access.");
                }
                return Observable.of(loginResponse);
            });

        return responseObservable;
    }

    /**
     * The request options for requesting restricted data using the access token! TODO! clean code.
     */
    accessTokenRequestOptionsObservable() : Observable<RequestOptions>{
        let optionsObservable : Observable<RequestOptions> = this.toAuthenticationResponseObservable().map((loginResponse: AuthenticationResponse)=> {
            let headers = new Headers({ 'Accept': 'application/json' });
            headers.append('X-Authorization', `Bearer ${loginResponse.token}`);
            let options = new RequestOptions({ headers: headers });
            return options;
        });
        return optionsObservable;
    }

    /**
     * The request options for requesting restricted data using the refresh token! TODO! clean code.
     */
    refreshTokenRequestOptionsObservable() : Observable<RequestOptions>{
        let optionsObservable : Observable<RequestOptions> = this.toAuthenticationResponseObservable().map((loginResponse: AuthenticationResponse)=> {
            let headers = new Headers({ 'Accept': 'application/json' });
            headers.append('R-Authorization', `Bearer ${loginResponse.refreshToken}`);
            let options = new RequestOptions({ headers: headers });
            return options;
        });
        return optionsObservable;
    }

    /**
     * Requests a new access token using from the refresh token api.
     */
    requestNewAccessKey() : Observable<string>  {

        let optionsObservable : Observable<RequestOptions> = this.refreshTokenRequestOptionsObservable();

        let httpObservable : Observable<Response> = optionsObservable.mergeMap((options: RequestOptions)=> {
            return this.http.get(AuthenticationService.REFRESH_TOKEN_URL, options);
        }).catch((response: Response) => {
            if (response.status != 200) {
                return Observable.throw('Error, refresh token invalid.');
            }
        });

        let newTokenObservable : Observable<string> = httpObservable.map((response: Response) => {
            let newAccessToken : string = response.json().token;
            return newAccessToken;
        });

        return newTokenObservable;
    }


    /**
     * Logs the user out.
     */
    remove():void {
        localStorage.removeItem('jwt');
    }

    /**
     * Checks to see if the user is signed in.
     * @returns {boolean} true if signed in, false if not.
     */
    isSignedIn():boolean {
        return localStorage.getItem('jwt') !== null;
    }
}
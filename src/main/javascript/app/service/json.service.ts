import {Headers, Http, RequestOptions, Response} from "@angular/http";
import {Observable} from "rxjs/Observable";
import "rxjs/Rx";
import {AuthenticationService} from "./authentication";

/**
 * Created by sean on 30/03/2017.
 *
 * An abstract class used to handle the import json requesting, posting ect.
 */
export abstract class JSONService {

    constructor(public http: Http, public authentication : AuthenticationService) {

    }

    /**
     * Gets a authorised json file from the restful interface.
     * @param url The url of the restful endpoint.
     */
    getAuthorised<T>(url: string): Observable<T> {
        let httpObservable : Observable<T> = this.authentication.accessTokenRequestOptionsObservable().flatMap((options:RequestOptions)=>{
            return this.http.get(url, options).map(res => {
                return <T> res.json()
            })
        });
        return httpObservable;
    }
}
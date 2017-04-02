import {Injectable} from "@angular/core";
import {NavigationStart, Router} from "@angular/router";
import {Observable} from "rxjs";
import {Subject} from "rxjs/Subject";

@Injectable()
export class AlertService {

    /**
     * The success alert.
     */
    private static SUCESS_ALERT : string = 'success';

    /**
     * The error alert.
     */
    private static ERROR_ALERT : string = 'error';

    /**
     * The list of subjects that can contain any type of information.
     */
    private subject = new Subject<any>();

    constructor(private router: Router) {
        router.events.subscribe((event : NavigationStart) => {
            this.subject.next();
        });
    }

    /**
     * Submits a success alert.
     * @param message The message of an successful alert.
     */
    success(message: string) {
        this.subject.next({ type: AlertService.SUCESS_ALERT, text: message });
    }

    /**
     * Submits a error alert.
     * @param message The message of an alert alert.
     */
    error(message: string) {
        this.subject.next({ type: AlertService.ERROR_ALERT, text: message });
    }

    /**
     * Transforms the subject to an Observable.
     */
    getMessage(): Observable<any> {
        return this.subject.asObservable();
    }
}

import {Component} from "@angular/core";
import {AlertService} from "../../service/alert.service";

@Component({
    selector: 'alert',
    templateUrl: 'app/component/alert/alert.component.html'
})
export class AlertComponent {
    /**
     * The message type can be any/
     */
    message: any;

    /**
     * References the alert service.
     * @param alertService The service that handles the alerts.
     */
    constructor(private alertService: AlertService) { }

    /**
     * Subscribes the message.
     */
    ngOnInit() {
        this.alertService.getMessage().subscribe(message => { this.message = message; });
    }
}
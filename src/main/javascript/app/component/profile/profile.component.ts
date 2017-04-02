import {Component, OnInit} from "@angular/core";
import {HttpModule, Response} from "@angular/http";
import {ProfileService} from "../../service/profile.service";
import {Profile} from "../../model/profile";

@Component({
    selector: 'server',
    templateUrl: 'app/component/profile/profile.component.html',
    styleUrls: ['app/component/profile/profile.component.css'],
    providers: [ProfileService, HttpModule]
})
export class ProfileComponent implements OnInit {

    constructor(private serverService: ProfileService) {

    }

    private profile: Profile = null;

    private errorMessage: string;

    ngOnInit() {
        return this.serverService.getLoggedInProfile()
            .subscribe((profile: Profile) => {
                    this.profile = profile;
                    console.log(this.profile.username);
                },
                error =>
                    this.errorMessage = <any>error);
    }

    getUsername() : String {
        return this.profile.username;
    }

}
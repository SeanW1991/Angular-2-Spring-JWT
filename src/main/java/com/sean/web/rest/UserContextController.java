package com.sean.web.rest;

import com.sean.web.authentication.token.JwtAccountAuthentication;
import com.sean.web.authentication.token.JwtTokenAuthentication;
import com.sean.web.model.AccountDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
/**
 * @author Sean
 *
 * A dedicated {@link RestController} used to retreive the information about the logged in user.
 */
public class UserContextController {

    @RequestMapping(value="/api/me", method=RequestMethod.GET)
    public @ResponseBody
    AccountDetails get(JwtAccountAuthentication account) {
        return (AccountDetails) account.getPrincipal();
    }
}

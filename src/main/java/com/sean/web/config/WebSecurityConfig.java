package com.sean.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sean.web.authentication.PathRequestValidation;
import com.sean.web.authentication.login.LoginAuthenticationProvider;
import com.sean.web.authentication.login.LoginProcessingFilter;
import com.sean.web.authentication.token.JwtAuthenticationProvider;
import com.sean.web.authentication.token.JwtTokenAuthenticationProcessingFilter;
import com.sean.web.authentication.token.validation.TokenValidator;
import com.sean.web.rest.RestAuthenticationEntryPoint;
import com.sean.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * @author Sean
 *
 *
 * An implementation of {@link WebSecurityConfigurerAdapter} used to configure the Spring Security to
 * use the Restful API for the login.
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationSuccessHandler successHandler;

    @Autowired
    private AuthenticationFailureHandler failureHandler;

    @Autowired
    private LoginAuthenticationProvider loginAuthenticationProvider;

    @Autowired
    private JwtAuthenticationProvider tokenAuthenticationProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenValidator tokenValidator;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public LoginProcessingFilter loginProcessingFilter() throws Exception {
        LoginProcessingFilter filter = new LoginProcessingFilter(JwtConstants.LOGIN_LOCATION, objectMapper, userService, successHandler, failureHandler);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }

    @Bean
    public JwtTokenAuthenticationProcessingFilter jwtTokenAuthenticationProcessingFilter() throws Exception {
        JwtTokenAuthenticationProcessingFilter filter = new JwtTokenAuthenticationProcessingFilter(failureHandler, tokenValidator, new PathRequestValidation());
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(loginAuthenticationProvider);
        auth.authenticationProvider(tokenAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
         //Disables  CSRF as we do not need it because we are using the restful api.
        .csrf().disable()

         // Enables exception handling and sets the entry point of any unauthorised access.
        .exceptionHandling()
        .authenticationEntryPoint(new RestAuthenticationEntryPoint())


        .and()
                //session management is used to create a stateless session, no session
                //will be created. Because we are using a stateful rest service, the server
                //should never rely on previous information to fulfill its request.
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        .and()
                // Allow the login api location to be accessful from everyone.
            .authorizeRequests()
                .antMatchers(JwtConstants.LOGIN_LOCATION).permitAll()
        .and()
                //Make any api locations require authentcation.
            .authorizeRequests()
                .antMatchers(JwtConstants.API_LOCATION).authenticated()
        .and()
                // We finally att the login and token filters before the {@link BasicAuthenticationFilter}.
            .addFilterBefore(loginProcessingFilter(), BasicAuthenticationFilter.class)
            .addFilterBefore(jwtTokenAuthenticationProcessingFilter(), BasicAuthenticationFilter.class);
    }
}

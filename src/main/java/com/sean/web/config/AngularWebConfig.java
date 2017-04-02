package com.sean.web.config;

import org.springframework.boot.autoconfigure.web.ErrorViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Collections;

/**
 * Created by sean on 26/03/2017.
 *
 *
 * The implementation of {@link WebMvcConfigurerAdapter} used for the angular 2 handling of views.
 * This is mainly to prevent view errors occuring when refreshing with an angular 2 front-end.
 */
@Configuration
public class AngularWebConfig extends WebMvcConfigurerAdapter {

    /**
     * The index html page.
     */
    private static final String INDEX_HTML = "index.html";

    @Bean
    ErrorViewResolver supportPathBasedLocationStrategyWithoutHashes() {
        return (request, status, model) -> status == HttpStatus.NOT_FOUND
                ? new ModelAndView(INDEX_HTML, Collections.<String, Object>emptyMap(), HttpStatus.OK)
                : null;
    }

}

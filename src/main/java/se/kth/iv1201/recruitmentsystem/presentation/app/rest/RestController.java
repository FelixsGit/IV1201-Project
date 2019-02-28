package se.kth.iv1201.recruitmentsystem.presentation.app.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.kth.iv1201.recruitmentsystem.application.ApplicationService;
import se.kth.iv1201.recruitmentsystem.domain.PersonDTO;
import se.kth.iv1201.recruitmentsystem.security.CustomUserDetailsService;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    private static final String API_URL = "/api/";

    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    /*
    @GetMapping(API_URL + "login")
    public PersonDTO login(@RequestParam(value="username") String username,
                              @RequestParam(value="password") String password) {
        // customUserDetailsService.loadUserByUsername(username);
        System.out.println("Login by " + username + ", " + password);
        return applicationService.findPerson(username);
    }
    */

    @GetMapping(API_URL + "applications")
    public PersonDTO login(@RequestParam(value="username") String username,
                           @RequestParam(value="password") String password) {
        // customUserDetailsService.loadUserByUsername(username);
        System.out.println("Login by " + username + ", " + password);
        return applicationService.findPerson(username);
    }


}

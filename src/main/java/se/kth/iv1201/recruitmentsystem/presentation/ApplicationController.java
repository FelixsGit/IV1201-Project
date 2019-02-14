package se.kth.iv1201.recruitmentsystem.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import se.kth.iv1201.recruitmentsystem.application.ApplicationService;
import se.kth.iv1201.recruitmentsystem.domain.UserException;

import javax.validation.Valid;

@Controller
@Scope("session")
public class ApplicationController {

    static final String DEFAULT_PAGE_URL = "/";
    static final String REGISTER_PAGE_URL = "register";
    static final String LOGIN_PAGE_URL = "login";
    static final String APPLICATION_PAGE_URL = "apply";

    private static String REGISTER_FORM_OBJ_NAME = "registrationForm";
    private static String LOGIN_FORM_OBJ_NAME = "loginForm";
    private static String APPLICATION_FORM_OBJ_NAME = "applicationForm";

    @Autowired
    private ApplicationService applicationService;

    ///////////////////////////////////GET MAPPINGS/////////////////////////////////////////
    /**
     * No page is specified, redirect to the welcome page.
     * @return A response that redirects the browser to the welcome page.
     */
    @GetMapping(DEFAULT_PAGE_URL)
    public String showDefaultView() {
        return "redirect:" + REGISTER_PAGE_URL;
    }

    /**
     * A get request for the registration page.
     * @param model Model objects used in the registration page.
     * @return The registration page url.
     */
    @GetMapping(REGISTER_PAGE_URL)
    public String showRegisterView(Model model) {
        if(!model.containsAttribute(REGISTER_FORM_OBJ_NAME)) {
            model.addAttribute(new RegistrationForm());
        }
        return REGISTER_PAGE_URL;
    }

    /**
     * A get request for the login page.
     * @param model Model objects used in the login page.
     * @return The login page url.
     */
    @GetMapping(LOGIN_PAGE_URL)
    public String showLoginView(Model model){
        if(!model.containsAttribute(LOGIN_FORM_OBJ_NAME)){
            model.addAttribute(new LoginForm());
        }
        return LOGIN_PAGE_URL;
    }

    /**
     * A get request for the application page.
     * @param model Model objects used in the Apply page.
     * @return The apply page url.
     */
    @GetMapping(APPLICATION_PAGE_URL)
    public String showApplyView(Model model) {
        if(!model.containsAttribute(APPLICATION_FORM_OBJ_NAME)) {
            model.addAttribute(new ApplicationForm());
        }
        return APPLICATION_PAGE_URL;
    }

    ///////////////////////////////////POST MAPPINGS/////////////////////////////////////////
    /**
     * The registration form has been submitted.
     * @param registrationForm Content of the registration form.
     * @param bindingResult Validation result for the registration form.
     * @param model Model objects used by the registration page.
     * @return The registration page url.
     */
    @PostMapping(DEFAULT_PAGE_URL + REGISTER_PAGE_URL)
    public String registerUser(@Valid @ModelAttribute RegistrationForm registrationForm, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()) {
            return REGISTER_PAGE_URL;
        }
        try {
            applicationService.createPerson(registrationForm.getName(), registrationForm.getSurname(), registrationForm.getSsn(),
                    registrationForm.getEmail(), registrationForm.getPassword(), "applicant", registrationForm.getUsername());
        } catch (UserException e) {
            System.out.println(e.getMessage());
            return REGISTER_PAGE_URL;
        }
        model.addAttribute(new RegistrationForm());
        return showLoginView(model);
    }

    /**
     * The login form has been submitted.
     * @param loginForm Content of the login form.
     * @param bindingResult Validation result for the login form.
     * @param model Model objects used by the login page.
     * @return The Apply page url
     */
    @PostMapping(DEFAULT_PAGE_URL + LOGIN_PAGE_URL)
    public String loginUser(@Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return LOGIN_PAGE_URL;
        }
        /**Stuff*/
        model.addAttribute(new LoginForm());
        return showApplyView(model) ;
    }

    /**
     * The application from has been submitted.
     * @param applicationForm Content of the application form.
     * @param bindingResult Validation result fro the application form.
     * @param model Model objects used by the application page.
     * @return The application page url.
     */

    @PostMapping(DEFAULT_PAGE_URL + APPLICATION_PAGE_URL)
    public String applyUser(@Valid @ModelAttribute ApplicationForm applicationForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return APPLICATION_PAGE_URL;
        }
        /**stuff*/
        model.addAttribute(new ApplicationForm());
        return APPLICATION_PAGE_URL;
    }

}

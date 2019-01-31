package se.kth.iv1201.recruitmentsystem.presentation;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@Scope("session")
public class ApplicationController {


    static final String DEFAULT_PAGE_URL = "/";
    static final String REGISTER_PAGE_URL = "register";
    static final String LOGIN_PAGE_URL = "login";
    static final String MODIFYAPPLICATION_PAGE_URL = "modifyApplication";
    private static String REGISTER_FORM_OBJ_NAME = "registrationForm";
    private static String LOGIN_FORM_OBJ_NAME = "loginForm";
    private static String APPLICATION_FORM_OBJ_NAME = "applicationForm";

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
     * @param model Model objects used in the application page.
     * @return The application page url.
     */
    @GetMapping(MODIFYAPPLICATION_PAGE_URL)
    public String showModifyApplicationView(Model model) {
        if(model.containsAttribute(APPLICATION_FORM_OBJ_NAME)) {
            model.addAttribute(new ApplicationForm());
        }
        return MODIFYAPPLICATION_PAGE_URL;
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
    public String registerUser(@Valid @ModelAttribute RegistrationForm registrationForm, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return REGISTER_PAGE_URL;
        }
        /**Stuff*/
        model.addAttribute(new RegistrationForm());
        return REGISTER_PAGE_URL;
    }

    /**
     * The login form has been submitted.
     * @param loginForm Content of the login form.
     * @param bindingResult Validation result for the login form.
     * @param model Model objects used by the login page.
     * @return The ModifyApplication page url
     */
    @PostMapping(DEFAULT_PAGE_URL + LOGIN_PAGE_URL)
    public String loginUser(@Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return LOGIN_PAGE_URL;
        }
        /**Stuff*/
        model.addAttribute(new LoginForm());
        return MODIFYAPPLICATION_PAGE_URL ;
    }

    /**
     * The application from has been submitted.
     * @param applicationForm Content of the application form.
     * @param bindingResult Validation result fro the application form.
     * @param model Model objects used by the application page.
     * @return The application page url.
     */
    @PostMapping(DEFAULT_PAGE_URL + MODIFYAPPLICATION_PAGE_URL)
    public String modifyApplication(@Valid @ModelAttribute ApplicationForm applicationForm, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return MODIFYAPPLICATION_PAGE_URL;
        }
        /**stuff*/
        model.addAttribute(new ApplicationForm());
        return MODIFYAPPLICATION_PAGE_URL;
    }
}

package se.kth.iv1201.recruitmentsystem.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import se.kth.iv1201.recruitmentsystem.application.ApplicationService;
import se.kth.iv1201.recruitmentsystem.domain.UserException;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@Scope("session")
public class ApplicationController {

    private static final String DEFAULT_PAGE_URL = "/";
    private static final String REGISTER_PAGE_URL = "register";
    private static final String LOGIN_PAGE_URL = "login";
    private static final String APPLICATION_PAGE_URL = "apply";
    private static final String HANDLE_APPLICATION_PAGE_URL = "handleApplication";
    private static final String LOGIN_OK_URL = "loginOk";

    private static String REGISTER_FORM_OBJ_NAME = "registrationForm";
    private static String LOGIN_FORM_OBJ_NAME = "loginForm";
    private static String APPLICATION_FORM_OBJ_NAME = "applicationForm";


    @Autowired
    private ApplicationService applicationService;

    ///////////////////////////////////GET MAPPINGS/////////////////////////////////////////

    /**
     * A get request for the loginOk page, this page is used only as a redirect page.
     * Used when a user have just logged in and is redirected based on his role.
     * @param request HttpServletRequest object provided by spring
     * @return new url based on role.
     */
    @GetMapping(DEFAULT_PAGE_URL + LOGIN_OK_URL)
    public String defaultAfterLogin(HttpServletRequest request) {
        if (request.isUserInRole("ROLE_applicant")) {
            return "redirect:" + APPLICATION_PAGE_URL;
        }
        return "redirect:"+ HANDLE_APPLICATION_PAGE_URL;
    }
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
    @GetMapping(DEFAULT_PAGE_URL + APPLICATION_PAGE_URL)
    public String showApplyView(Model model) {
        if(!model.containsAttribute(APPLICATION_FORM_OBJ_NAME)) {
            model.addAttribute(new ApplicationForm());
        }
        return APPLICATION_PAGE_URL;
    }

    /**
     * A get request for the application page.
     * @return TODO  functionality on this page
     */
    @GetMapping(DEFAULT_PAGE_URL + HANDLE_APPLICATION_PAGE_URL)
    public String showHandleApplicationView(){
        return HANDLE_APPLICATION_PAGE_URL;
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
                    registrationForm.getEmail(), registrationForm.getPassword(), "recruit", registrationForm.getUsername());
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
        System.out.println(loginForm.getPassword());
        if(bindingResult.hasErrors()){
            return LOGIN_PAGE_URL;
        }
        try {
            applicationService.loginUser(loginForm.getUsername(), loginForm.getPassword());
        } catch (LoginException e) {
            System.out.println(e.getMessage());
        }
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

package se.kth.iv1201.recruitmentsystem.presentation.app;

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
import se.kth.iv1201.recruitmentsystem.presentation.error.ExceptionHandlers;

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
     * Used when a user have just logged in, and it redirects based on the role on the user.
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
     * A get request for the app page.
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
    public String registerUser(@Valid @ModelAttribute RegistrationForm registrationForm, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return REGISTER_PAGE_URL;
        }
        try {
            applicationService.createPerson(registrationForm.getName(), registrationForm.getSurname(), registrationForm.getSsn(),
                    registrationForm.getEmail(), registrationForm.getPassword(), "applicant", registrationForm.getUsername());
            model.addAttribute(new RegistrationForm());
        } catch (UserException exception) {
            regErrorHandling(exception, model);
            return REGISTER_PAGE_URL;
        }
        return showLoginView(model);
    }

    private void regErrorHandling(UserException exception, Model model) {
        if(exception.getMessage().toUpperCase().contains("USERNAME")) {
            model.addAttribute(ExceptionHandlers.ERROR_TYPE_KEY, ExceptionHandlers.USERNAME_FAIL);
        } else if (exception.getMessage().toUpperCase().contains("EMAIL")){
            model.addAttribute(ExceptionHandlers.ERROR_TYPE_KEY, ExceptionHandlers.EMAIL_FAIL);
        } else if (exception.getMessage().toUpperCase().contains("ROLE")){
            model.addAttribute(ExceptionHandlers.ERROR_TYPE_KEY, ExceptionHandlers.ROLE_FAIL);
        } else {
            model.addAttribute(ExceptionHandlers.ERROR_TYPE_KEY, ExceptionHandlers.GENERIC_ERROR);
        }
    }

    /**
     * The app from has been submitted.
     * @param applicationForm Content of the app form.
     * @param bindingResult Validation result fro the app form.
     * @param model Model objects used by the app page.
     * @return The app page url.
>>>>>>> aa844a625aa83b75c4fa2f1a5d5e6d314d4692c1:src/main/java/se/kth/iv1201/recruitmentsystem/presentation/app/ApplicationController.java
     */
    @PostMapping(DEFAULT_PAGE_URL + APPLICATION_PAGE_URL)
    public String applyUser(@Valid @ModelAttribute ApplicationForm applicationForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return APPLICATION_PAGE_URL;
        }
        //TODO add functionality here
        model.addAttribute(new ApplicationForm());
        return APPLICATION_PAGE_URL;
    }

}

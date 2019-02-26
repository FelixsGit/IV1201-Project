package se.kth.iv1201.recruitmentsystem.presentation.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import se.kth.iv1201.recruitmentsystem.application.ApplicationService;
import se.kth.iv1201.recruitmentsystem.domain.*;
import se.kth.iv1201.recruitmentsystem.presentation.error.ExceptionHandlers;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;


/**
 *This controller class handles all http request to content root.
 */
@Controller
@Scope("session")
public class ApplicationController {

    public static final String DEFAULT_PAGE_URL = "/";
    public static final String REGISTER_PAGE_URL = "register";
    public static final String LOGIN_PAGE_URL = "login";
    public static final String APPLICATION_PAGE_URL = "apply";
    public static final String HANDLE_APPLICATION_PAGE_URL = "handleApplication";
    public static final String SEARCH_APPLICATION_PAGE_URL = "searchApplication";
    public static final String LOGIN_OK_URL = "loginOk";
    public static final String UPDATE_ACCOUNT_URL = "updateAccount";

    private static String REGISTER_FORM_OBJ_NAME = "registrationForm";
    private static String LOGIN_FORM_OBJ_NAME = "loginForm";
    private static String APPLICATION_FORM_OBJ_NAME = "applicationForm";
    private static String UPDATE_ACCOUNT_FORM_OBJ_NAME = "UpdateAccountForm";
    private static String HANDLE_APPLICATION_OBJ_NAME = "handleApplicationForm";
    private static String SEARCH_APPLICATION_OBJ_NAME = "searchApplicationForm";


    @Autowired
    private ApplicationService applicationService;

    private PersonDTO person;

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
        return "redirect:"+ SEARCH_APPLICATION_PAGE_URL;
    }

    @GetMapping(DEFAULT_PAGE_URL + UPDATE_ACCOUNT_URL)
    public String showUpdateAccountView(Model model){
        if(!model.containsAttribute(UPDATE_ACCOUNT_FORM_OBJ_NAME)){
            model.addAttribute(new UpdateAccountForm());
        }
        return UPDATE_ACCOUNT_URL;
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
     * A get request for the apply page.
     * @param updateAccountForm Content of the updateAccountForm
     * @param competenceForm Content of the competenceForm
     * @param model Model object used in apply page.
     * @param request HttpServletRequest object provided by Spring.
     * @return the apply page url.
     */
    @GetMapping(DEFAULT_PAGE_URL + APPLICATION_PAGE_URL)
    public String showApplyView(final UpdateAccountForm updateAccountForm, final CompetenceForm competenceForm, Model model, HttpServletRequest request) {
        if(!model.containsAttribute(APPLICATION_FORM_OBJ_NAME)) {
            model.addAttribute(new ApplicationForm());
            model.addAttribute(new UpdateAccountForm());
            model.addAttribute(new CompetenceForm());
        }
        List<Competence> competences = applicationService.findCompetences();
        competenceForm.setCompetences(competences);
        model.addAttribute("competenceForm", competenceForm);
        checkForNullValues(updateAccountForm, model, request);
        return APPLICATION_PAGE_URL;
    }

    /**
     * A private method that checks if  the user has null in either email and ssn.
     * @param updateAccountForm Content of the updateAccount form.
     * @param model Model object used in apply page.
     * @param request  HttpServletRequest object provided by spring.
     */
    private void checkForNullValues(final UpdateAccountForm updateAccountForm, Model model, HttpServletRequest request){
        PersonDTO person = applicationService.findPerson(request.getUserPrincipal().getName());
        if(person.getEmail() == null)
            updateAccountForm.setEmail("missingEmail");
        if(person.getSsn() == null)
            updateAccountForm.setSsn("missingSsn");

        model.addAttribute("updateAccountForm", updateAccountForm);
    }

    /**
     * A get request for the application page.
     * @return TODO  functionality on this page
     */
    @GetMapping(DEFAULT_PAGE_URL + HANDLE_APPLICATION_PAGE_URL)
    public String showHandleApplicationView(Model model){
        if(!model.containsAttribute(HANDLE_APPLICATION_OBJ_NAME)) {
            model.addAttribute(new HandleApplicationForm());
        }
        return HANDLE_APPLICATION_PAGE_URL;
    }

    /**
     * A get request for the searchApplication page.
     * @param updateAccountForm Content of the updateAccountForm
     * @param searchApplicationForm Content of the searchApplicationForm
     * @param model Model object that is used in the searchApplication page.
     * @param request HttpServletRequest object provided by spring
     * @return TODO
     */
    @GetMapping(DEFAULT_PAGE_URL + SEARCH_APPLICATION_PAGE_URL)
    public String showSearchApplicationView(final UpdateAccountForm updateAccountForm, final SearchApplicationForm searchApplicationForm, Model model, HttpServletRequest request){
        if(!model.containsAttribute(SEARCH_APPLICATION_OBJ_NAME)){
            model.addAttribute(new SearchApplicationForm());
        }
        List<ApplicationDTO> applicationDTOList = applicationService.getAllApplications();
        searchApplicationForm.setApplicationDTOList(applicationDTOList);
        checkForNullValues(updateAccountForm, model, request);
        model.addAttribute("updateAccountForm", updateAccountForm);
        model.addAttribute("searchApplicationForm", searchApplicationForm);
        return SEARCH_APPLICATION_PAGE_URL;
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
                    registrationForm.getEmail(), registrationForm.getPassword(), Role.APPLICANT, registrationForm.getUsername());
            model.addAttribute(new RegistrationForm());
        } catch (UserException exception) {
            regErrorHandling(exception, model);
            return REGISTER_PAGE_URL;
        }
        return showLoginView(model);
    }

    /**
     * The updateAccountForm that is used by the updateAccount page.
     * This form is just a dummy form for future development....
     * @param updateAccountForm Content of the updateAccountForm
     * @param bindingResult Validation result for the updateAccount page.
     * @param model object used by the updateAccount page.
     * @return login page.
     */
    @PostMapping(DEFAULT_PAGE_URL + UPDATE_ACCOUNT_URL)
    public String updateAccount(@Valid @ModelAttribute UpdateAccountForm updateAccountForm, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return UPDATE_ACCOUNT_URL;
        }
        //Add code here to send mail to user
        return showLoginView(model);
    }

    /**
     *This private method is used  for mapping different error messages depending on
     * the fields in the registration form.
     * @param exception the exception
     * @param model registration model  filled in by the user
     */
    private void regErrorHandling(Exception exception, Model model) {
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
     */
    @PostMapping(DEFAULT_PAGE_URL + APPLICATION_PAGE_URL)
    public String applyUser(@Valid @ModelAttribute ApplicationForm applicationForm, BindingResult bindingResult, Model model, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return showApplyView(new UpdateAccountForm(), new CompetenceForm(), model, request );
        }
        try {
            applicationService.createApplication(applicationForm.getCompetence(), applicationForm.getFromDate(), applicationForm.getToDate(),
                     applicationForm.getYearsOfExperience(), request.getUserPrincipal().getName());
        } catch (UserException | ApplicationException | ParseException exception) {
            regErrorHandling(exception, model);
        }
        model.addAttribute(new ApplicationForm());
        return "/applicationSent";
    }

}

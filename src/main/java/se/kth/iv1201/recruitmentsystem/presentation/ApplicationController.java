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
    private static String REGISTER_FORM_OBJ_NAME = "registrationForm";

    /**
     * No page is specified, redirect to the welcome page.
     *
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
     * The registration form has been submitted.
     * @param registrationForm Content of the registration form.
     * @param bindingResult Validation result for the registration form.
     * @param model Model objects used by the registration page.
     * @return
     */
    @PostMapping(DEFAULT_PAGE_URL + REGISTER_PAGE_URL)
    public String registerUser(@Valid @ModelAttribute RegistrationForm form, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return REGISTER_PAGE_URL;
        }
        /**Stuff*/
        model.addAttribute(new RegistrationForm());
        return REGISTER_PAGE_URL;
    }

}

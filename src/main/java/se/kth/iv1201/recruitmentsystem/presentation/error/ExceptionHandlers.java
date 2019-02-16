package se.kth.iv1201.recruitmentsystem.presentation.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import se.kth.iv1201.recruitmentsystem.presentation.app.ApplicationController;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@ControllerAdvice
public class ExceptionHandlers implements ErrorController  {
    static final String ERROR_PATH = "failure";
    public static final String ERROR_PAGE_URL = "error";
    public static final String ERROR_TYPE_KEY = "errorType";
    public static final String GENERIC_ERROR = "generic";
    public static final String USERNAME_FAIL = "Username already taken! Please select another.";
    public static final String EMAIL_FAIL = "Email already in use! Please select another.";
    public static final String ROLE_FAIL = "Role does not exist! Please select a feasible one.";
    //public static final String USERNAME_FAIL = "username";
    //public static final String EMAIL_FAIL = "email";
    //public static final String ROLE_FAIL = "role";

    @Override
    public String getErrorPath() {
        return "/" + ERROR_PATH;
    }

    /**
     * Exception handler for broken business rules.
     *
     * @return An appropriate error page.
     */
    /**@ExceptionHandler(UserException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(UserException exception, Model model) {
        System.out.println(exception.getMessage());
        System.out.println(exception.getMessage().toUpperCase().contains("EMAIL"));
        if(exception.getMessage().toUpperCase().contains("USERNAME")) {
            model.addAttribute(ERROR_TYPE_KEY, USERNAME_FAIL);
       } else if (exception.getMessage().toUpperCase().contains("EMAIL")){
            model.addAttribute(ERROR_TYPE_KEY, EMAIL_FAIL);
            System.out.println("Emailfail");
        } else if (exception.getMessage().toUpperCase().contains("ROLE")){
            model.addAttribute(ERROR_TYPE_KEY, ROLE_FAIL);
        } else {
            model.addAttribute(ERROR_TYPE_KEY, GENERIC_ERROR);
        }
        return ERROR_PAGE_URL;
    }*/


    @GetMapping("/"+ ERROR_PATH)
    public String handleHttpError(HttpServletRequest request, HttpServletResponse response, Model model) {
        ApplicationController controller = new ApplicationController();
        String statusCode = extractHttpStatusCode(request);
        model.addAttribute(ERROR_TYPE_KEY, statusCode);
        response.setStatus(Integer.parseInt(statusCode));
        return ERROR_PAGE_URL;
    }

    private String extractHttpStatusCode(HttpServletRequest request) {
        return request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE).toString();
    }
}

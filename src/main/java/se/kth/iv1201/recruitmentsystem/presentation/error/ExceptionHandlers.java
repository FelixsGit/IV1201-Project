package se.kth.iv1201.recruitmentsystem.presentation.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import se.kth.iv1201.recruitmentsystem.presentation.app.ApplicationController;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Contains all exception handling methods.
 */
@Controller
@ControllerAdvice
public class ExceptionHandlers implements ErrorController  {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlers.class);
    static final String ERROR_PATH = "failure";
    public static final String ERROR_PAGE_URL = "error";
    public static final String ERROR_TYPE_KEY = "errorType";
    public static final String GENERIC_ERROR = "Something went wrong! Please try again later.";
    public static final String USERNAME_FAIL = "Username already taken! Please select another.";
    public static final String EMAIL_FAIL = "Email already in use! Please select another.";
    public static final String SSN_FAIL = "Personal registration number has already been used. Please type in your own.";
    public static final String ROLE_FAIL = "Role does not exist! Please select a feasible one.";
    public static final String PERSON_FAIL = "Your profile can not be found in the database.";
    public static final String COMPETENCE_FAIL = "Invalid competence";

    @Override
    public String getErrorPath() {
        return "/" + ERROR_PATH;
    }

    /**
     * Exception handler for special generic errors.
     *
     * @return An appropriate error page.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Model model) {
        model.addAttribute(ERROR_TYPE_KEY, GENERIC_ERROR);
        return ERROR_PAGE_URL;
    }

    @GetMapping("/"+ ERROR_PATH)
    public String handleHttpError(HttpServletRequest request, HttpServletResponse response, Model model) {
        LOGGER.debug("Http error handler got Http status: {}", request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
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
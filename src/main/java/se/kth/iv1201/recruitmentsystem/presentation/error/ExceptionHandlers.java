package se.kth.iv1201.recruitmentsystem.presentation.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import se.kth.iv1201.recruitmentsystem.domain.UserException;

@Controller
@ControllerAdvice
public class ExceptionHandlers implements ErrorController  {
    static final String ERROR_PATH = "failure";
    public static final String ERROR_PAGE_URL = "error";
    public static final String ERROR_TYPE_KEY = "errorType";
    public static final String GENERIC_ERROR = "generic";
    public static final String REG_FAIL = "registration";

    @Override
    public String getErrorPath() {
        return "/" + ERROR_PATH;
    }

    /**
     * Exception handler for broken business rules.
     *
     * @return An appropriate error page.
     */
    @ExceptionHandler(UserException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(UserException exception, Model model) {
        if(exception.getMessage().toUpperCase().contains("REGISTRATION"))
            model.addAttribute(ERROR_TYPE_KEY, REG_FAIL);

        return ERROR_PAGE_URL;
    }
}

package recordstore.error;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class AppExceptionAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    public ModelAndView entityNotFoundHandler(EntityNotFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("/errorPages/error");
        modelAndView.getModel().put("message", ex.getMessage());
        return modelAndView;
    }
}

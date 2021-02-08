package recordstore.interceptor;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView handleMaxUploadSizeExceedException(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("errors/errorPage");
        modelAndView.getModel().put("message", "The file is too large to upload!");
        modelAndView.getModel().put("url", request.getRequestURI());
        return modelAndView;
    }

//    @ExceptionHandler(EntityNotFoundException.class)
//    public ModelAndView handleEntityNotFoundException(HttpServletRequest request) {
//        ModelAndView modelAndView = new ModelAndView("errors/errorPage");
//        modelAndView.getModel().put("message", "Something went wrong!");
//        modelAndView.getModel().put("url", "/");
//        return modelAndView;
//    }
}
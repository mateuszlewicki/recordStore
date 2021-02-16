package recordstore.interceptor;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class FileUploadExceptionAdvice {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView handleMaxSizeException(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("errorPages/uploadError");
        modelAndView.getModel().put("message", "The file is too large to upload!");
        modelAndView.getModel().put("url", request.getRequestURI());
        return modelAndView;
    }
}
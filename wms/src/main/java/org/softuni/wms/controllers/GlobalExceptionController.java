package org.softuni.wms.controllers;

import org.softuni.wms.errors.HttpError;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionController extends BaseController {

    private static final String DEFAULT_ERROR_MESSAGE = "There was an error with your request.";
    private static final String DEFAULT_FORBIDDEN_MESSAGE = "You do not have permission to access this page or service";

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView getException(RuntimeException e, HttpServletResponse response) {
        HttpError error = new HttpError();

        error.setStatus(e.getClass().isAnnotationPresent(ResponseStatus.class)
                ? e.getClass().getAnnotation(ResponseStatus.class).value().toString()
                : HttpStatus.BAD_REQUEST.toString());

        error.setDescription(e.getClass().isAnnotationPresent(ResponseStatus.class)
                ? e.getClass().getAnnotation(ResponseStatus.class).value().getReasonPhrase()
                :HttpStatus.BAD_REQUEST.getReasonPhrase());

        error.setMessage(
                e.getClass().isAnnotationPresent(ResponseStatus.class)
                        ? e.getClass().getAnnotation(ResponseStatus.class).reason()
                        : DEFAULT_ERROR_MESSAGE);

        response.setStatus(e.getClass().isAnnotationPresent(ResponseStatus.class)
                ? e.getClass().getAnnotation(ResponseStatus.class).code().value()
                : HttpStatus.BAD_REQUEST.value());

        return this.view("error/error-template", "error", error);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView getAccessDeniedException(HttpServletResponse response) {

        HttpError error = new HttpError();
        error.setStatus(HttpStatus.FORBIDDEN.toString());
        error.setDescription(HttpStatus.FORBIDDEN.getReasonPhrase());
        error.setMessage(DEFAULT_FORBIDDEN_MESSAGE);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        return this.view("error/error-template", "error", error);
    }
}

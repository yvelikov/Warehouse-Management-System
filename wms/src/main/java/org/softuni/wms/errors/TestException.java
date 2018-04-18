package org.softuni.wms.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.I_AM_A_TEAPOT, reason = "Test Exception")
public class TestException extends RuntimeException{

}

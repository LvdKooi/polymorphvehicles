package nl.kooi.vehicle.api;

import nl.kooi.vehicle.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleNotFoundException(NotFoundException exc) {
        var detail = ProblemDetail.forStatus(404);
        detail.setTitle("Not Found");
        detail.setDetail(exc.getMessage());
        return detail;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(RuntimeException.class)
    public ProblemDetail handleRuntimeException(RuntimeException exc) {
        var detail = ProblemDetail.forStatus(400);
        detail.setTitle("Bad request");
        detail.setDetail(exc.getMessage());
        return detail;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception exc) {
        var detail = ProblemDetail.forStatus(500);
        detail.setTitle("Internal Server Error");
        detail.setDetail(exc.getMessage());
        return detail;
    }

}

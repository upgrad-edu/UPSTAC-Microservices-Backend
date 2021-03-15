package org.upgrad.upstac.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;

public class UpgradResponseStatusException extends ResponseStatusException {


    public UpgradResponseStatusException(HttpStatus status) {
        super(status, null, null);
    }


    public UpgradResponseStatusException(HttpStatus status, @Nullable String reason) {
        super(status, reason, null);
    }


    public UpgradResponseStatusException(HttpStatus status, @Nullable String reason, @Nullable Throwable cause) {
        super(status, reason, cause);
    }



    public static UpgradResponseStatusException asForbidden(String msg) {
        return asExceptionFromHttpStatus(msg, HttpStatus.FORBIDDEN);
    }
    public static UpgradResponseStatusException asBadRequest(String msg) {
        return asExceptionFromHttpStatus(msg, HttpStatus.BAD_REQUEST);
    }
    public static UpgradResponseStatusException asBadRequest(String msg, Throwable throwable) {
        return new UpgradResponseStatusException( HttpStatus.BAD_REQUEST,msg,throwable);
    }

    public static UpgradResponseStatusException asServerError(String msg) {
        return asExceptionFromHttpStatus(msg, HttpStatus.INTERNAL_SERVER_ERROR);
    }
   public static UpgradResponseStatusException asNoContent(String msg) {
        return asExceptionFromHttpStatus(msg, HttpStatus.BAD_REQUEST);
    }


    public static UpgradResponseStatusException asConstraintViolation(ConstraintViolationException e) {

        return  new UpgradResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(),e);
    }

    public static UpgradResponseStatusException asExceptionFromHttpStatus(String msg, HttpStatus httpStatus) {
        return new UpgradResponseStatusException(httpStatus, msg);
    }


}

package org.anonymous.email.exceptions;

import org.anonymous.global.exceptions.BadRequestException;

public class AuthCodeMismatchException extends BadRequestException {
    public AuthCodeMismatchException() {
        super("Mismatch.authCode");
        setErrorCode(true);
    }
}
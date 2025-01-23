package org.anonymous.email.exceptions;

import org.anonymous.global.exceptions.BadRequestException;

public class AuthCodeExpiredException extends BadRequestException {
    public AuthCodeExpiredException() {
        super("Expired.authCode");
        setErrorCode(true);
    }
}
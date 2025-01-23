package org.anonymous.email.exceptions;

import org.anonymous.global.exceptions.BadRequestException;

public class AuthCodeIssueException extends BadRequestException {
    public AuthCodeIssueException() {
        super("Fail.authCode.issue");
        setErrorCode(true);
    }
}

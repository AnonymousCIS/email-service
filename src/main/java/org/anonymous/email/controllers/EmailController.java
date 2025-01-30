package org.anonymous.email.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.anonymous.email.exceptions.AuthCodeIssueException;
import org.anonymous.email.services.EmailAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class EmailController {
    private final EmailAuthService authService;

    @Operation(summary = "코드 발송", description = "회원가입시 이메일 인증코드 발송")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @GetMapping("/auth/{to}")
    public void authCode(@PathVariable("to") String to) {
        if (!authService.sendCode(to)) {
            throw new AuthCodeIssueException();
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @GetMapping("/verify")
    public void verify(@RequestParam(name="authCode", required = false) Integer authCode) {
        authService.verify(authCode);
    }
}

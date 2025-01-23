package org.anonymous.email.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequiredArgsConstructor
public class EmailController {
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @GetMapping("/auth/{to}")
    public void authCode(@PathVariable("to") String to) {

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @GetMapping("/verify")
    public void verify(@RequestParam(name="authCode", required = false) Integer authCode) {

    }
}

package org.anonymous.email.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
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

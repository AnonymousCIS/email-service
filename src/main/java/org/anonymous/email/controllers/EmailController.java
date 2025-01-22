package org.anonymous.email.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.anonymous.email.exceptions.AuthCodeIssueException;
import org.anonymous.email.services.EmailAuthService;
import org.anonymous.email.services.EmailService;
import org.anonymous.global.libs.Utils;
import org.anonymous.global.rests.JSONData;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class EmailController {
    private final EmailAuthService authService;
    private final EmailService emailService;
    /**
     * 인증코드 발급
     *
     * @param to
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @GetMapping("/auth/{to}")
    public void authCode(@PathVariable("to") String to) {
        if (!authService.sendCode(to)) {
            throw new AuthCodeIssueException();
        }
    }
    /**
     * 발급받은 인증코드 검증
     *
     * @param authCode
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @GetMapping("/verify")
    public void verify(@RequestParam(name="authCode", required = false) Integer authCode) {
        authService.verify(authCode);
    }
    /**
     * 메일 전송하기
     *
     * @param form
     */
    @PostMapping({"", "/tpl/{tpl}"})
    public void sendEmail(@PathVariable(name="tpl", required = false) String tpl, @RequestPart(name="file", required = false) List<MultipartFile> files, @ModelAttribute RequestEmail form) {
        form.setFiles(files);
        tpl = StringUtils.hasText(tpl) ? tpl : "general";
        emailService.sendEmail(form, tpl);
    }
}

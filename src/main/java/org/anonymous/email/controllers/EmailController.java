package org.anonymous.email.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.anonymous.email.exceptions.AuthCodeIssueException;
import org.anonymous.email.services.EmailAuthService;
import org.anonymous.email.services.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "이메일 인증 API", description = "회원가입 시 이메일 인증 코드 보내주는 컨트롤러")
@RestController
@RequiredArgsConstructor
public class EmailController {
    private final EmailAuthService authService;
    private final EmailService emailService;

    /**
     * 인증코드 발급
     *
     * @param to
     */
    @Operation(summary = "코드 발송", description = "회원가입시 이메일 인증코드 발송")
    @ApiResponse(responseCode = "204")
    @Parameter(name = "to", description = "요청자 이메일")
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
    @Operation(summary = "코드 인증", description = "회원가입시 발급받은 인증코드를 인증")
    @ApiResponse(responseCode = "204")
    @Parameter(name = "authCode", description = "인증코드", required = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @GetMapping("/verify")
    public void verify(@RequestParam(name = "authCode", required = false) Integer authCode) {
        authService.verify(authCode);
    }

    /**
     * 메일 전송하기
     *
     * @param form
     */
    @PostMapping({"/send", "/tpl/{tpl}"})
    public void sendEmail(@PathVariable(name = "tpl", required = false) String tpl, @RequestPart(name = "file", required = false) List<MultipartFile> files, @ModelAttribute RequestEmail form) {
        form.setFiles(files);
        tpl = StringUtils.hasText(tpl) ? tpl : "general";
        emailService.sendEmail(form, tpl);
    }
}
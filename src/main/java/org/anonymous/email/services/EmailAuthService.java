package org.anonymous.email.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.anonymous.email.constants.AuthStatus;
import org.anonymous.email.controllers.RequestEmail;
import org.anonymous.email.entities.Log;
import org.anonymous.email.exceptions.AuthCodeExpiredException;
import org.anonymous.email.exceptions.AuthCodeMismatchException;
import org.anonymous.email.repositories.LogRepository;
import org.anonymous.global.exceptions.BadRequestException;
import org.anonymous.global.libs.Utils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailAuthService {
    private final Utils utils;
    private final EmailService emailService;
    private final LogService logService; // LogService 의존성 주입

    // 인증 코드 발송
    public boolean sendCode(String to) {
        Random random = new Random();
        String subject = utils.getMessage("Email.authCode.subject");

        Integer authCode = random.nextInt(10000, 99999);
        LocalDateTime expired = LocalDateTime.now().plusMinutes(3L);
        LocalDateTime requestTime = LocalDateTime.now(); // 요청 시간 기록
        // 인증 코드와 만료 시간 저장
        utils.saveValue(utils.getUserHash() + "_authCode", authCode);
        utils.saveValue(utils.getUserHash() + "_expiredTime", expired);

        Map<String, Object> tplData = new HashMap<>();
        tplData.put("authCode", authCode);

        RequestEmail form = new RequestEmail();
        form.setTo(List.of(to));
        // 이메일 전송
        boolean isSent = emailService.sendEmail(form, "auth", tplData);

        // 이메일 전송 후 로그 기록
        if (isSent) {
            logService.logStatus(to, AuthStatus.REQUESTED, requestTime); // 인증 요청 상태 기록
        }
        return isSent;
    }

    public void verify(Integer code) {
        if (code == null) {
            throw new BadRequestException(utils.getMessage("NotBlank.authCode"));
        }

        LocalDateTime expired = utils.getValue(utils.getUserHash() + "_expiredTime");
        Integer authCode = utils.getValue(utils.getUserHash() + "_authCode");

        if (expired != null && expired.isBefore(LocalDateTime.now())) {
            logService.logStatus(null, AuthStatus.EXPIRED ,LocalDateTime.now()); // 인증 만료 상태 기록
            throw new AuthCodeExpiredException();
        }

        if (authCode == null) {
            throw new BadRequestException();
        }

        if (!code.equals(authCode)) {
            logService.logStatus(null, AuthStatus.FAILED, LocalDateTime.now()); // 인증 실패 상태 기록
            throw new AuthCodeMismatchException();
        }

        // 인증 코드 검증 성공
        utils.saveValue(utils.getUserHash() + "_authCodeVerified", true);
        logService.logStatus(null, AuthStatus.VERIFIED, LocalDateTime.now()); // 인증 완료 상태 기록
    }
}
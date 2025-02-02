package org.anonymous.email.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.anonymous.email.entities.EmailLog;
import org.anonymous.email.services.LogDeleteService;
import org.anonymous.email.services.LogInfoService;
import org.anonymous.global.paging.ListData;
import org.anonymous.global.rests.JSONData;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "ADMIN LOGINFO API", description = "관리자 전용 LOG 조회 기능.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminEmailController {
    private final LogInfoService logInfoService;

    /**
     * 로그 단일 & 목록 조회
     *
     * @param seq
     * @return
     */
    // 단일조회
    @Operation(summary = "로그 단일조회", description = "관리자가 이메일 인증을 요청한 사람의 이메일 로그를 조회할 수 있습니다.")
    @Parameters({
            @Parameter(name = "seq", description = "로그 ID", required = true, example = "1")

    })
    @GetMapping("/view/{seq}")
    public JSONData view(@PathVariable("seq") Long seq) {
        EmailLog emailLog = logInfoService.get(seq);

        return new JSONData(emailLog);
    }
    //목록조회
    @Operation(summary = "로그 목록조회", description = "관리자가 이메일 인증을 요청한 사람의 로그를 목록으로 검색 및 조회 할 수 있습니다.")
    @Parameters({
            @Parameter(name = "search", description = "Log 검색 조회"),
            @Parameter(name = "sopt", description = "검색 옵션 ", example = "TO"),
            @Parameter(name = "skey", description = "검색 키워드", example = "user01@test.org"),
            @Parameter(name = "page", description = "페이지 번호", example = "1"),
            @Parameter(name = "limit", description = "페이지 크기", example = "20"),
            @Parameter(name = "to", description = "이메일 수신자"),
            @Parameter(name = "subject", description = "이메일 제목"),
            @Parameter(name = "content", description = "이메일 내용")

    })
    @GetMapping("/list")
    public JSONData list(@ModelAttribute LogSearch search) {
        ListData<EmailLog> items = logInfoService.getList(search);

        return new JSONData(items);
    }
}
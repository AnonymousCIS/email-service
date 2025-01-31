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

@Tag(name = "ADMIN LOGINFO API", description = "관리자 전용 LOG 조회 기능.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminEmailController {
    private final LogInfoService logInfoService;
    private final LogDeleteService deleteService;

    /**
     * 로그 단일 & 목록 조회
     *
     * @param seq
     * @return
     */
    @Operation(summary = "로그 단일조회", description = "관리자가 이메일 인증을 요청한 사람의 이메일 로그를 조회할 수 있습니다.")
    @Parameters({
            @Parameter(name = "seq", description = "로그 ID", required = true, example = "1125")

    })
    @GetMapping("/view/{seq}")
    public JSONData view(@PathVariable("seq") Long seq) {
        EmailLog emailLog = logInfoService.get(seq);

        return new JSONData(emailLog);
    }
    //목록조회
    @Operation(summary = "로그 목록조회", description = "관리자가 이메일 인증을 요청한 사람의 로그를 목록으로 검색 및 조회 할 수 있습니다.")
    @Parameters({
            @Parameter(name = "search", description = "Log 검색 조회", examples = {
                    @ExampleObject(name = "to", value = "to"),
                    @ExampleObject(name = "subject", value = "subject"),
                    @ExampleObject(name = "content", value = "content")
            })

    })
    @GetMapping("/list")
    public JSONData list(@ModelAttribute LogSearch search) {
        ListData<EmailLog> items = logInfoService.getList(search);

        return new JSONData(items);
    }

    /**
     * 1년뒤 자동 삭제
     *
     * @return
     */
    @DeleteMapping("/logDelete")
    public JSONData delete(){

        //return new JSONData(deleteService.delete());
        return null;
    }
}
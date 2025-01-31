package org.anonymous.email.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.anonymous.email.entities.Log;
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
    private final LogDeleteService deleteService;

    /**
     * 로그 단일 & 목록 조회
     *
     * @param seq
     * @return
     */
    @Operation(summary = "로그 단일 & 목록 조회", description = "관리자가 이메일 인증을 요청한 사람의 로그를 조회할 수 있습니다.")
    @Parameters({
            @Parameter(name = "search", description = "LOG 단일 & 목록 조회"),
            @Parameter(name = "seq", description = "LOG ID", required = true, examples = {
                    @ExampleObject(name = "to", value = "to"),
                    @ExampleObject(name = "requested", value = "requested"),
                    @ExampleObject(name = "expired", value = "expired"),
                    @ExampleObject(name = "failed", value = "failed"),
            })
    })
    @GetMapping("/view/{seq}")
    public JSONData view(@PathVariable("seq") Long seq) {
        Log log = logInfoService.get(seq);

        return new JSONData(log);
    }

    //목록조회
    @GetMapping("/list")
    public JSONData list(@ModelAttribute LogSearch search) {
        ListData<Log> items = logInfoService.getList(search);

        return new JSONData(items);
    }

    /**
     * 1년뒤 자동 삭제
     *
     * @return
     */
    @DeleteMapping("/logDelete")
    public JSONData delete(){

        return new JSONData(deleteService.delete());
    }
}
package org.anonymous.email.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminEmailController {

    // 단일 조회
    @GetMapping("view/{seq}")
    public void logView(@PathVariable("seq") Long seq) {

    }

    //목록조회
    @GetMapping("list")
    public void logList(Long seq) {

    }
}

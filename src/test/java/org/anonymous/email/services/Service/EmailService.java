package org.anonymous.email.services.Service;

import jakarta.servlet.http.Cookie;
import org.anonymous.email.services.EmailAuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles({"default", "email"})
public class EmailService {
    @Autowired
    private EmailAuthService service;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void test1() throws Exception{
        String userKey = "" + Objects.hash("userHash");

        Cookie cookie = new Cookie(userKey, "1");

        mockMvc.perform(get("/auth/ckxodlf12@naver.com")
                        .cookie(cookie))
                .andDo(print());
    }
}

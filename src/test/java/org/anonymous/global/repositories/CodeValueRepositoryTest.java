package org.anonymous.global.repositories;

import org.anonymous.global.entities.CodeValue;
import org.anonymous.global.libs.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CodeValueRepositoryTest {
    @Autowired
    private CodeValueRepository codeValueRepository;

    @Autowired
    private Utils utils;

    @Test
    void test() {
        CodeValue item = new CodeValue();
        item.setCode("1234");
        item.setValue("5678");
        codeValueRepository.save(item);

        CodeValue item2 = codeValueRepository.findByCode(item.getCode());
        System.out.println(item2);
    }

    @Test
    void test2() {
        utils.saveValue("1234", "5678");
        String value = utils.getValue("1234");
        System.out.println(value);
    }
}


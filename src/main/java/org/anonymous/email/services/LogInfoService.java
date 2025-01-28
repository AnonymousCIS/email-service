package org.anonymous.email.services;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.anonymous.email.controllers.LogSearch;
import org.anonymous.email.entities.Log;
import org.anonymous.email.entities.QLog;
import org.anonymous.email.repositories.LogRepository;
import org.anonymous.global.paging.ListData;
import org.anonymous.global.paging.Pagination;
import org.anonymous.member.MemberUtil;
import org.anonymous.member.contants.Authority;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

@Lazy
@Service
@RequiredArgsConstructor
public class LogInfoService {
    private final LogRepository repository;
    private final JPAQueryFactory queryFactory;
    private final MemberUtil memberUtil;
    private final HttpServletRequest request;

    // 단일 조회.
    public Log get(Long seq) {

        Log log = repository.findById(seq).orElseThrow(RuntimeException::new);

        addInfo(log); // 추가 정보 처리

        return log;
    }

    public ListData<Log> getList(LogSearch search) {
        int page = Math.max(search.getPage(), 1);
        int limit = search.getLimit();
        limit = limit < 1 ? 20 : limit;

        BooleanBuilder andBuilder = new BooleanBuilder();
        QLog log = QLog.log;

        /* 검색 처리 S */
        String sopt = search.getSopt();
        String skey = search.getSkey();
        sopt = StringUtils.hasText(sopt) ? sopt : "TO";  // sopt 값이 없으면 "TO"로 설정

        if (StringUtils.hasText(skey)) {
            StringExpression condition = null;

            if (sopt.equals("TO")) {  // TO만 검색
                condition = log.to;
            }

            andBuilder.and(Objects.requireNonNull(condition).contains(skey.trim()));
        }
        /* 검색 처리 E */

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Order.desc("requestTime")));  // 정렬 기준을 요청시간으로 설정
        Page<Log> data = repository.findAll(andBuilder, pageable);

        List<Log> items = data.getContent();
        items.forEach(this::addInfo);

        Pagination pagination = new Pagination(page, (int) data.getTotalElements(), 10, limit, request);

        return new ListData<>(items, pagination);
    }

    public void addInfo(Log item) {

    }
}
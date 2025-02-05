package org.anonymous.email.services;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.anonymous.email.controllers.LogSearch;
import org.anonymous.email.entities.EmailLog;
import org.anonymous.email.entities.QEmailLog;
import org.anonymous.email.repositories.LogRepository;
import org.anonymous.global.exceptions.BadRequestException;
import org.anonymous.global.paging.ListData;
import org.anonymous.global.paging.Pagination;
import org.anonymous.member.MemberUtil;
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
    public EmailLog get(Long seq) {
        if (!memberUtil.isAdmin()){
            throw new BadRequestException();
        }
        EmailLog emailLog = repository.findById(seq).orElseThrow(RuntimeException::new);

        addInfo(emailLog); // 추가 정보 처리

        return emailLog;
    }
    //
    // 게시판 목록조회 검색을 통해 누가 요청했는지 또는 요청자의 요청 상태로 검색 가능.
    public ListData<EmailLog> getList(LogSearch search) {
        if (!memberUtil.isAdmin()){
            throw new BadRequestException();
        }
        int page = Math.max(search.getPage(), 1);
        int limit = search.getLimit();
        limit = limit < 1 ? 20 : limit;

        BooleanBuilder andBuilder = new BooleanBuilder();
        QEmailLog log = QEmailLog.emailLog;

        /* 검색 처리 S */
        String sopt = search.getSopt();
        String skey = search.getSkey();
        sopt = StringUtils.hasText(sopt) ? sopt : "ALL";
        if (StringUtils.hasText(skey)) {
            StringExpression condition = null;
            if (sopt.equals("TO")) {  // TO만 검색
                condition = log.to;
            }   else if (sopt.equals("SUBJECT")){
                condition = log.subject;
            }   else if (sopt.equals("CONTENT")){
                condition = log.content;
            } else if (sopt.equals("CREATEDAT")) {
                condition = log.createdAt.stringValue();
            } else {
                condition = log.to.concat(log.subject).concat(log.content).concat(log.createdAt.stringValue());
            }

            andBuilder.and(Objects.requireNonNull(condition).contains(skey.trim()));
        }
        /* 검색 처리 E */

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Order.desc("createdAt")));  // 정렬 기준을 요청시간으로 설정
        Page<EmailLog> data = repository.findAll(andBuilder, pageable);

        List<EmailLog> items = data.getContent();
        items.forEach(this::addInfo);

        Pagination pagination = new Pagination(page, (int) data.getTotalElements(), 10, limit, request);

        return new ListData<>(items, pagination);
    }

    public void addInfo(EmailLog item) {

    }
}
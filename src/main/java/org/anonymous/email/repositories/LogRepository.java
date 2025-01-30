package org.anonymous.email.repositories;

import org.anonymous.email.entities.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.time.LocalDateTime;

public interface LogRepository extends JpaRepository<Log, Long>, QuerydslPredicateExecutor<Log> {
    void deleteByTimeStamp(LocalDateTime localDateTime);
}
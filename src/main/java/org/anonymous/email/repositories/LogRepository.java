package org.anonymous.email.repositories;

import org.anonymous.email.entities.EmailLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface LogRepository extends JpaRepository<EmailLog, Long>, QuerydslPredicateExecutor<EmailLog> {

}
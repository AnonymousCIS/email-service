package org.anonymous.email.controllers;

import lombok.Data;
import org.anonymous.email.constants.AuthStatus;
import org.anonymous.global.paging.CommonSearch;

import java.util.List;

@Data
public class LogSearch extends CommonSearch {
    private List<String> to;
    private List<AuthStatus> statuses;
}

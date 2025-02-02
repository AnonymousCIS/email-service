package org.anonymous.email.controllers;

import lombok.Data;
import org.anonymous.global.paging.CommonSearch;

import java.util.List;

@Data
public class LogSearch extends CommonSearch {
    private List<String> to;
    private List<String> subject;
    private List<String> content;
}

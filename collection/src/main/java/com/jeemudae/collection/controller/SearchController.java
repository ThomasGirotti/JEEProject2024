package com.jeemudae.collection.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SearchController {
    public SearchController() {
    }

    @GetMapping("/search")
    public String search() {
        return "search";
    }
}

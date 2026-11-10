package ru.ksenia.NauJava.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/report")
public class ReportViewController {
    @GetMapping("")
    public String showReportPage(){
        return "report";
    }
}

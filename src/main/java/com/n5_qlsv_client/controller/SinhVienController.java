package com.n5_qlsv_client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class SinhVienController {

    @GetMapping
    public String homePage(){

        return "index";
    }
}

package com.n5_qlsv_client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hoc-phan")
public class HocPhanController {

    @GetMapping("/hoc-phan-khung")
    public String hocPhanKhung(Model model){
        model.addAttribute("TrangHienTai","Học Phần Khung");
        return "hoc-phan-khung";
    }

    @GetMapping("/dang-ky-hoc-phan")
    public String dangKyHocPhan(Model model){
        model.addAttribute("TrangHienTai","Đăng Ký Học Phần");
        return "dang-ky-hoc-phan";
    }
}

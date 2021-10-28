package com.n5_qlsv_client.controller;

import com.n5_qlsv_client.service.LopHocPhanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/hoc-phan/dang-ky-hoc-phan")
public class DangKyHocPhanController {

    @Autowired
    private LopHocPhanService lopHocPhanService;

    @GetMapping
    public String hocKyLopHocPhan(Model model){
        model.addAttribute("HKLHP", lopHocPhanService.getAllLopHocPhans());
        return "dang-ky-hoc-phan";
    }

    @GetMapping("/dslhp")
    public String danhsachLHP(Model model, @RequestParam(name = "maHK") long maHK){
        model.addAttribute("danhsachLHP", lopHocPhanService.findLHPByMaHK(maHK));
        return "dang-ky-hoc-phan";
    }

}

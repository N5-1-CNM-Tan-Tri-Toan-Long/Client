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
    public String hocKyLopHocPhan(Model model, Long maHK){
        if(maHK != null){
            model.addAttribute("danhsachLHP", lopHocPhanService.findLHPByMaHK(maHK));
            model.addAttribute("HKLHP", lopHocPhanService.getAllLopHocPhans());

        }else {
            model.addAttribute("HKLHP", lopHocPhanService.getAllLopHocPhans());
        }

        model.addAttribute("maHK", maHK);
        model.addAttribute("TrangHienTai","Đăng Ký Học Phần");
        return "dang-ky-hoc-phan";
    }


}

package com.n5_qlsv_client.controller;

import com.n5_qlsv_client.model.SinhVien;
import com.n5_qlsv_client.service.SinhVienService;
import com.n5_qlsv_client.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    private SinhVienService sinhVienService;
    @GetMapping
    public String adminPage(Model model, Principal principal, HttpSession session) {

        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        SinhVien sinhVien = sinhVienService.findById(loginedUser.getUsername());
        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo", userInfo);
        session.setAttribute("maSV", sinhVien.getMaSV());
        model.addAttribute("sinhvien", sinhVien);

        return "index";
    }

    @GetMapping(value = "/login")
    public String loginPage(Model model) {

        return "login";
    }

    @GetMapping(value = "/logoutSuccessful")
    public String logoutSuccessfulPage(Model model) {
        model.addAttribute("messege", "Sai tài khoản hoặc mật khẩu");
        return "login";
    }

    @GetMapping(value = "/403")
    public String accessDenied(Model model, Principal principal) {

        if (principal != null) {
            SinhVien sinhVien = sinhVienService.findById(principal.getName());

                String message = "<b style='font-size: 20px;'>Xin lỗi</b> <br><b style='color: red; font-size: 25px'>" + sinhVien.getTenSV()
                    + "</b><br> <p style='font-size: 20px;'>Bạn không có quyền truy cập vào trang này!</p>";
            model.addAttribute("message", message);
        }

        return "403Page";
    }
}

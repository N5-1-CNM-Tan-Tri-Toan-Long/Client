package com.n5_qlsv_client.controller;

import com.n5_qlsv_client.model.SinhVien;
import com.n5_qlsv_client.service.SinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.security.Principal;


@Controller
@RequestMapping("/doi-mat-khau")
public class DoiMatKhauController {

    @Autowired
    private SinhVienService sinhVienService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @GetMapping
    public String doiMatKhau(Model model, HttpSession session){
        String maSV = (String) session.getAttribute("maSV");
        SinhVien sinhVien = sinhVienService.findById(maSV);

        model.addAttribute("TrangHienTai", "Đổi mật khẩu");
        model.addAttribute("sinhVien", sinhVien);
        return "doi-mat-khau";
    }

    @PostMapping
    public String changePassword(HttpSession session, Model model,
                                 @RequestParam(value = "oldpassword", required = false) String oldPassword,
                                 @RequestParam(value = "newpassword", required = false) String newPassword,
                                 @RequestParam(value = "repassword", required = false) String rePassword,
                                 RedirectAttributes redirectAttributes, Principal principal){
        System.out.println(oldPassword);
        System.out.println(newPassword);
        System.out.println(rePassword);
        String maSV = (String) session.getAttribute("maSV");
        SinhVien sinhVien = sinhVienService.findById(maSV);
        User user = (User) userDetailsService.loadUserByUsername(principal.getName());
        if (bCryptPasswordEncoder.matches(oldPassword, user.getPassword()) && newPassword.equals(rePassword)){
            sinhVien.setPassword(new BCryptPasswordEncoder().encode(newPassword));
            System.out.println("Thành công");
            sinhVienService.saveSinhVien(sinhVien);
            redirectAttributes.addFlashAttribute("mess", "Đổi mật khẩu thành công!");
            redirectAttributes.addFlashAttribute("suc_err", "success");
        }else {
            System.out.println("Thất bại");
            redirectAttributes.addFlashAttribute("mess", "Đổi mật khẩu thất bại!");
            redirectAttributes.addFlashAttribute("suc_err", "error");
        }
        return "redirect:/doi-mat-khau";
    }
}

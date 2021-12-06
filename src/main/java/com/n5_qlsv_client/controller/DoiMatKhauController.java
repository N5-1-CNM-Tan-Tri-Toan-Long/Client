package com.n5_qlsv_client.controller;

import com.n5_qlsv_client.model.SinhVien;
import com.n5_qlsv_client.service.SinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
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

//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String doiMatKhau(Model model, Principal principal){

        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        SinhVien sinhVien = sinhVienService.findById(loginedUser.getUsername());
        model.addAttribute("tensinhvien", sinhVien.getTenSV());

        model.addAttribute("TrangHienTai", "Đổi mật khẩu");
        model.addAttribute("sinhVien", sinhVien);
        return "doi-mat-khau";
    }

    @PostMapping
    public String changePassword(@RequestParam(value = "oldpassword", required = false) String oldPassword,
                                 @RequestParam(value = "newpassword", required = false) String newPassword,
                                 @RequestParam(value = "repassword", required = false) String rePassword,
                                 RedirectAttributes redirectAttributes, Principal principal){

        //Lấy mã sinh viên thông qua login principal
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        String maSV = loginedUser.getUsername();

        SinhVien sinhVien = sinhVienService.findById(maSV);
        User user = (User) userDetailsService.loadUserByUsername(principal.getName());
        if (passwordEncoder.matches(oldPassword, user.getPassword())){
            if(newPassword.equals(rePassword)){
                sinhVien.setPassword(passwordEncoder.encode(newPassword));
                sinhVienService.saveSinhVien(sinhVien);
                redirectAttributes.addFlashAttribute("mess", "Đổi mật khẩu thành công!");
                redirectAttributes.addFlashAttribute("suc_err", "success");
            }else {
                redirectAttributes.addFlashAttribute("mess", "Mật khẩu mới và xác nhận không khớp!");
                redirectAttributes.addFlashAttribute("suc_err", "warning");
            }
        }else {
            redirectAttributes.addFlashAttribute("mess", "Mật khẩu cũ không đúng!");
            redirectAttributes.addFlashAttribute("suc_err", "warning");
        }
        return "redirect:/doi-mat-khau";
    }
}

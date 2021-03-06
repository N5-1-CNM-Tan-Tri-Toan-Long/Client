package com.n5_qlsv_client.controller;

import com.n5_qlsv_client.model.SinhVien;
import com.n5_qlsv_client.service.SinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/thong-tin-sinh-vien")
public class ThongTinSinhVienController {

    @Autowired
    private SinhVienService sinhVienService;

    @GetMapping
    public String findSinhVienById(Model model, Principal principal){

        //Lấy mã sinh viên thông qua login principal
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        String maSV = loginedUser.getUsername();
        SinhVien sinhVien = sinhVienService.findById(maSV);

        model.addAttribute("tensinhvien", sinhVien.getTenSV());
        model.addAttribute("sinhvien", sinhVien);
        model.addAttribute("TrangHienTai","Thông Tin Sinh Viên");
        return "thong-tin-sinh-vien";
    }

    @PostMapping
    String luuThongTinSV(SinhVien sinhVien, RedirectAttributes redirectAttributes) {
        try{
            sinhVienService.saveSinhVien(sinhVien);
            redirectAttributes.addFlashAttribute("mess", "Cập nhật thông tin cá nhân thành công");
            redirectAttributes.addFlashAttribute("suc_err", "success");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("mess", "Đã có lỗi xảy ra");
            redirectAttributes.addFlashAttribute("suc_err", "error");
        }

        return "redirect:/thong-tin-sinh-vien";
    }
}

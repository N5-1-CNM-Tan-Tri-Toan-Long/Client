package com.n5_qlsv_client.controller;

import com.n5_qlsv_client.model.HocKyLHP;
import com.n5_qlsv_client.model.KetQuaHocTap;
import com.n5_qlsv_client.model.LopHocPhanTheoHK;
import com.n5_qlsv_client.model.SinhVien;
import com.n5_qlsv_client.service.*;
import com.n5_qlsv_client.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    private SinhVienService sinhVienService;

    @Autowired
    private HocKyService hocKyService;

    @Autowired
    private LichHocSinhVienService lichHocSinhVienService;

    @Autowired
    private ChuyenNganhService chuyenNganhService;

    @Autowired
    private KetQuaHocTapService ketQuaHocTapService;
    SinhVien sinhVien;
    @GetMapping
    public String adminPage(Model model, Principal principal, HttpSession session,
                            Long maHK) {

        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        sinhVien = sinhVienService.findById(loginedUser.getUsername());
        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo", userInfo);
        session.setAttribute("maSV", sinhVien.getMaSV());
        session.setAttribute("tensinhvien", sinhVien.getTenSV());
        model.addAttribute("sinhvien", sinhVien);
        model.addAttribute("maHK", maHK);
        model.addAttribute("HocKyLHP", hocKyLHPS(sinhVien.getMaSV()));

        //So tc sinh vien da hoc
        AtomicInteger soTC = new AtomicInteger();// Số tín chỉ theo chuyên ngành
        chuyenNganhService.getAllChuyenNganhs().forEach(chuyenNganh -> {
            if (chuyenNganh.getTenChuyenNganh().equals(sinhVien.getChuyenNganh().getTenChuyenNganh())){
                soTC.set(chuyenNganh.getSoTC());
            }
        });
        model.addAttribute("soTCTheoCN", soTC);
        //So tin chỉ đã học.
        int soTCDaHoc = 0;
        List<KetQuaHocTap> ketQuaHocTaps = ketQuaHocTapService.findKQHTByMaSV(sinhVien.getMaSV());
        List<Integer> ketQuaHocTaps1 = new ArrayList<>();
            for (KetQuaHocTap ketQuaHocTap : ketQuaHocTaps){
                int i = ketQuaHocTap.getLopHocPhan().getHocPhan().getSoTCLT() + ketQuaHocTap.getLopHocPhan().getHocPhan().getSoTCTH();
                ketQuaHocTaps1.add(i);
            }
            // Cộng các tín chỉ lấy được trong mảng
            for (int num : ketQuaHocTaps1){
                soTCDaHoc += num;
            }
        model.addAttribute("soTCDaHoc", soTCDaHoc);
            // tính phần trăm

        return "index";
    }

    @PostMapping("/LHPInHK")
    @ResponseBody
    public Set<LopHocPhanTheoHK> lopHocPhanTheoHKS(@RequestParam("maHK") Long maHK){
        Set<LopHocPhanTheoHK> list = new HashSet<>();
        lichHocSinhVienService.getLichHocByMaSV(sinhVien.getMaSV()).forEach(lichHocSinhVien -> {
            if (lichHocSinhVien.getChiTietLopHocPhan().getLopHocPhan().getHocKy().getMaHK() == maHK)
                list.add(new LopHocPhanTheoHK(lichHocSinhVien.getChiTietLopHocPhan().getLopHocPhan().getMaLHP(),
                        lichHocSinhVien.getChiTietLopHocPhan().getLopHocPhan().getTenLHP(),
                        lichHocSinhVien.getChiTietLopHocPhan().getLopHocPhan().getHocPhan().getSoTCLT()+
                        lichHocSinhVien.getChiTietLopHocPhan().getLopHocPhan().getHocPhan().getSoTCTH()));
        });
        return list;
    }
    List<HocKyLHP> hocKyLHPS(String maSV) {
        //học kỳ học phần
        List<HocKyLHP> hocKyList = new ArrayList<>();
        SinhVien sv = sinhVienService.findById(maSV);
        int gioiHanHK = convertToLocalDate(sv.getNgayVaoTruong()).getYear() + 6;
        hocKyService.getAllHocKys().forEach(hocKy -> {
            if (convertToLocalDate(sv.getNgayVaoTruong()).getYear() <= hocKy.getNamBatDau() && gioiHanHK > hocKy.getNamBatDau())
                hocKyList.add(new HocKyLHP(hocKy.getMaHK(), hocKy.getMoTa(), hocKy.getNamBatDau(),
                        hocKy.getNamKetThuc(), hocKy.getThuTuHocKy()));
        });
        return hocKyList;
    }

    //Lấy học kỳ hiện tại

    public LocalDate convertToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
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

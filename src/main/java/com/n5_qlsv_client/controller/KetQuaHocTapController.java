package com.n5_qlsv_client.controller;

import com.n5_qlsv_client.model.SinhVien;
import com.n5_qlsv_client.service.SinhVienService;
import com.n5_qlsv_client.service.impl.MaHKTheoKQHT;
import com.n5_qlsv_client.model.KetQuaHocTap;
import com.n5_qlsv_client.service.HocKyService;
import com.n5_qlsv_client.service.KetQuaHocTapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/ket-qua-hoc-tap")
public class KetQuaHocTapController {

    @Autowired
    private KetQuaHocTapService ketQuaHocTapService;

    @Autowired
    private HocKyService hocKyService;

    @Autowired
    private MaHKTheoKQHT maHKTheoKQHT;

    @Autowired
    private SinhVienService sinhVienService;

    @GetMapping
    public String findKQHTByMaSV(Model model, Principal principal){

        //Lấy mã sinh viên thông qua login principal
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        String maSV = loginedUser.getUsername();

        SinhVien sinhVien = sinhVienService.findById(maSV);
        model.addAttribute("tensinhvien", sinhVien.getTenSV());

        List<KetQuaHocTap> ketQuaHocTaps = ketQuaHocTapService.findKQHTByMaSV(maSV);
        model.addAttribute("TrangHienTai","Kết Quả Học Tập");
        model.addAttribute("KQHTs", ketQuaHocTaps);
        model.addAttribute("HK", hocKyService.getAllHocKys());
        // tra ve danh sach ma hoc
        model.addAttribute("HKSV", maHKTheoKQHT.getMaHKTheoKQHT(maSV));

//        List<Long> maHKTheoKQHT1 = maHKTheoKQHT.getMaHKTheoKQHT(maSV);
//        Set<KetQuaHocTap> ketQuaHocTaps1 = new HashSet<>();
//        for (Long hocKy : maHKTheoKQHT1){
//            for (KetQuaHocTap ketQuaHocTap : ketQuaHocTaps){
//                if (ketQuaHocTap.getLopHocPhan().getHocKy().getMaHK() == hocKy){
//                    ketQuaHocTaps1.add(ketQuaHocTap);
//                }
//            }
//        }
//        model.addAttribute("stt", ketQuaHocTaps1.size());
        return "ket-qua-hoc-tap";
    }

    @GetMapping("/in-lich")
    public String inKetQuaHocTap(Model model, HttpSession session) {
        String maSV = (String) session.getAttribute("maSV");
        List<KetQuaHocTap> ketQuaHocTaps = ketQuaHocTapService.findKQHTByMaSV(maSV);
        model.addAttribute("TrangHienTai","Kết Quả Học Tập");
        model.addAttribute("KQHTs", ketQuaHocTaps);
        model.addAttribute("HK", hocKyService.getAllHocKys());
        model.addAttribute("HKSV", maHKTheoKQHT.getMaHKTheoKQHT(maSV));
        model.addAttribute("sinhvien", sinhVienService.findById(maSV));
        return "in-ket-qua-hoc-tap";
    }

}

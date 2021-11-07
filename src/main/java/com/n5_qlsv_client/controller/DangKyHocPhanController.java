package com.n5_qlsv_client.controller;

import com.n5_qlsv_client.model.CTLHPChoDangKy;
import com.n5_qlsv_client.model.ChiTietLopHocPhan;
import com.n5_qlsv_client.model.LopHocPhan;
import com.n5_qlsv_client.model.LopHocPhanChoDangKy;
import com.n5_qlsv_client.service.CTLHPService;
import com.n5_qlsv_client.service.HocPhanService;
import com.n5_qlsv_client.service.LopHocPhanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/hoc-phan/dang-ky-hoc-phan")
public class DangKyHocPhanController {

    @Autowired
    private LopHocPhanService lopHocPhanService;

    @Autowired
    private HocPhanService hocPhanService;

    @Autowired
    private CTLHPService ctlhpService;

    private Logger logger = LoggerFactory.getLogger(DangKyHocPhanController.class);

    @GetMapping
    public String hocKyLopHocPhan(Model model, Long maHK) {
        if (maHK != null) {
            model.addAttribute("danhsachHP", hocPhanService.findHPByMaHK(maHK));
            model.addAttribute("HKLHP", lopHocPhanService.getAllLopHocPhans());

        } else {
            model.addAttribute("HKLHP", lopHocPhanService.getAllLopHocPhans());
        }

        model.addAttribute("maHK", maHK);
        model.addAttribute("TrangHienTai", "Đăng Ký Học Phần");
        return "dang-ky-hoc-phan";
    }

    @PostMapping("/lop-hoc-phan")
    @ResponseBody
    public List<LopHocPhanChoDangKy> lopHocPhan(@RequestParam("maHocPhan") String maHocPhan, @RequestParam("maHocKy") long maHocKy) {
        List<LopHocPhanChoDangKy> list = new ArrayList<>();
        lopHocPhanService.findByMaHocPhan(maHocPhan).forEach(lopHocPhan -> {
            if (lopHocPhan.getHocKy().getMaHK() == maHocKy)
                list.add(new LopHocPhanChoDangKy(lopHocPhan.getMaLHP(), lopHocPhan.getTenLHP(),
                        lopHocPhan.getTrangThai(), lopHocPhan.getTenVietTat(),
                        lopHocPhan.getSoLuongDangKyHienTai(), lopHocPhan.getSoLuongDangKyToiDa()));
        });
        return list;
    }

    @PostMapping("/chi-tiet-lop-hoc-phan")
    @ResponseBody
    public List<CTLHPChoDangKy> findByMaLopHocPhan(@RequestParam("maLHP") long id) {
        List<CTLHPChoDangKy> list = new ArrayList<>();
        ctlhpService.findByMaLopHocPhan(id).forEach(chiTietLopHocPhan -> {
            list.add(new CTLHPChoDangKy(chiTietLopHocPhan.getTietHoc(), chiTietLopHocPhan.getCoSo(),
                    chiTietLopHocPhan.getPhong(), chiTietLopHocPhan.getGiangVien().getTenGV(),
                    convertToLocalDate(chiTietLopHocPhan.getNgayBatDau()),
                    convertToLocalDate(chiTietLopHocPhan.getNgayKetThuc())));
        });
        return list;
    }

    public LocalDate convertToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}

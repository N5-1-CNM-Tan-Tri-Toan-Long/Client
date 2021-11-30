package com.n5_qlsv_client.controller;

import com.n5_qlsv_client.model.*;
import com.n5_qlsv_client.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Controller
@RequestMapping("/hoc-phan/dang-ky-hoc-phan")
public class DangKyHocPhanController {

    @Autowired
    private LopHocPhanService lopHocPhanService;

    @Autowired
    private HocPhanService hocPhanService;

    @Autowired
    private CTLHPService ctlhpService;

    @Autowired
    private HocKyService hocKyService;

    @Autowired
    private LichHocSinhVienService lichHocSinhVienService;

    @Autowired
    private SinhVienService sinhVienService;

    @Autowired
    private KetQuaHocTapService ketQuaHocTapService;

    private Logger logger = LoggerFactory.getLogger(DangKyHocPhanController.class);
    private Set<HocPhanDaDangKy> list;
    private Long maHocKy;

    @GetMapping
    public String hocKyLopHocPhan(Model model, Long maHK, HttpSession session) {
        String maSV = (String) session.getAttribute("maSV");
        if (maHK != null) {
            list = new HashSet<>();
            lichHocSinhVienService.getLichHocByMaSV(maSV).forEach(lichHocSinhVien -> {
                if (lichHocSinhVien.getChiTietLopHocPhan().getLopHocPhan().getHocKy().getMaHK() == maHK)
                    list.add(new HocPhanDaDangKy(lichHocSinhVien.getChiTietLopHocPhan().getLopHocPhan().getMaLHP(),
                            lichHocSinhVien.getChiTietLopHocPhan().getLopHocPhan().getTenLHP(),
                            lichHocSinhVien.getChiTietLopHocPhan().getLopHocPhan().getTenVietTat(),
                            lichHocSinhVien.getChiTietLopHocPhan().getLopHocPhan().getTrangThai(),
                            convertToLocalDate(lichHocSinhVien.getNgayDangKyHP()),
                            lichHocSinhVien.getChiTietLopHocPhan().getLopHocPhan().getHocPhan().getSoTCLT() +
                                    lichHocSinhVien.getChiTietLopHocPhan().getLopHocPhan().getHocPhan().getSoTCTH(),
                            lichHocSinhVien.getChiTietLopHocPhan().getLopHocPhan().getHocPhan().getMaHocPhan()));
            });
            model.addAttribute("listHocPhanDaDK", list);

            Set<HocPhan> listHP = new HashSet<>();
            hocPhanService.findHPByMaHK(maHK).forEach(hocPhan -> {
                if (checkHocPhanTrongList(hocPhan.getMaHocPhan(), list))
                    listHP.add(hocPhan);
            });
            model.addAttribute("danhsachHP", listHP);
        }
        model.addAttribute("HKLHP", hocKyLHPS(maSV));
        maHocKy = maHK;
        model.addAttribute("maHK", maHocKy);
        return "dang-ky-hoc-phan";
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

    private boolean checkHocPhanTrongList(String maHP, Set<HocPhanDaDangKy> list) {
        AtomicBoolean ketQua = new AtomicBoolean(true);
        list.forEach(hocPhanDaDangKy -> {
            if (hocPhanDaDangKy.getMaHocPhan().equals(maHP))
                ketQua.set(false);
        });
        return ketQua.get();
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
            list.add(new CTLHPChoDangKy(chiTietLopHocPhan.getNhomTH(), chiTietLopHocPhan.getTietHoc(), chiTietLopHocPhan.getCoSo(),
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

    @PostMapping("/dang-ky")
    public String dangKyLopHocPhan(@RequestParam("maHocKy") long maHK, @RequestParam("maLHP") long maLHP,
                                   @RequestParam(name = "nhomTH", defaultValue = "0") int nhomTH, HttpSession session) {
        String maSV = (String) session.getAttribute("maSV");

        SinhVien sinhVien = sinhVienService.findById(maSV);
        LopHocPhan lopHocPhan = lopHocPhanService.findById(maLHP);
        int toiDa = lopHocPhan.getSoLuongDangKyToiDa(), hienTai = lopHocPhan.getSoLuongDangKyHienTai();
        LichHocSinhVien lichHocSinhVien = new LichHocSinhVien();
        lichHocSinhVien.setNgayDangKyHP(new Date());

        if (!lopHocPhanService.findById(maLHP).getTrangThai().equalsIgnoreCase("Chờ sinh viên đăng ký"))
            return "redirect:/hoc-phan/dang-ky-hoc-phan?maHK=" + maHK;

        if (kiemTraLichTrung(ctlhpService.findByMaLopHocPhan(maLHP), lichHocSinhVienService.getLichHocByMaSV(maSV), nhomTH).size() > 0)
            return "redirect:/hoc-phan/dang-ky-hoc-phan?maHK=" + maHK;

        if (hienTai + 1 >= toiDa)
            return "redirect:/hoc-phan/dang-ky-hoc-phan?maHK=" + maHK;
        lopHocPhan.setSoLuongDangKyHienTai(hienTai + 1);
        lopHocPhanService.saveLopHocPhan(lopHocPhan);

        ctlhpService.findByMaLopHocPhan(maLHP).forEach(chiTietLopHocPhan -> {
            if (chiTietLopHocPhan.getNhomTH() == nhomTH || chiTietLopHocPhan.getNhomTH() == 0) {
                lichHocSinhVien.setChiTietLopHocPhan(chiTietLopHocPhan);
                lichHocSinhVien.setSinhVien(sinhVien);
                lichHocSinhVienService.saveLHSV(lichHocSinhVien);
            }
        });
        return "redirect:/hoc-phan/dang-ky-hoc-phan?maHK=" + maHK;
    }

    @GetMapping(value = "/xoa-dang-ky")
    public String deleteLHSV(@RequestParam("maLHP") long maLHP, @RequestParam("maHocKy") long maHK, HttpSession session) {
        String maSV = (String) session.getAttribute("maSV");
        LopHocPhan lopHocPhan = lopHocPhanService.findById(maLHP);
        int hienTai = lopHocPhan.getSoLuongDangKyHienTai();
        lopHocPhan.setSoLuongDangKyHienTai(hienTai - 1);
        lopHocPhanService.saveLopHocPhan(lopHocPhan);

        lichHocSinhVienService.getLichHocByMaSV(maSV).forEach(lichHocSinhVien -> {
            if (lichHocSinhVien.getChiTietLopHocPhan().getLopHocPhan().getMaLHP() == lopHocPhan.getMaLHP()) {
                lichHocSinhVienService.deleteLHSV(lichHocSinhVien.getMaLHSV());
            }
        });
        return "redirect:/hoc-phan/dang-ky-hoc-phan?maHK=" + maHK;
    }

    @PostMapping("/kiem-tra-trung")
    @ResponseBody
    public Set<HocPhanTrung> kiemTraHocPhanTrung(@RequestParam("maLHP") long maLHP, @RequestParam(name = "nhomTH",
            defaultValue = "0") int nhomTH, HttpSession session) {
        String maSV = (String) session.getAttribute("maSV");
        Set<HocPhanTrung> hocPhanTrungs = new HashSet<>();
        hocPhanTrungs.add(new HocPhanTrung("Môn học không được phép đăng ký", null, null, null, null));
        if (!lopHocPhanService.findById(maLHP).getTrangThai().equalsIgnoreCase("Chờ sinh viên đăng ký"))
            return hocPhanTrungs;
        List<ChiTietLopHocPhan> listCTHP = ctlhpService.findByMaLopHocPhan(maLHP);
        List<LichHocSinhVien> listLH = lichHocSinhVienService.getLichHocByMaSV(maSV);
        return kiemTraLichTrung(listCTHP, listLH, nhomTH);
    }

    private Set<HocPhanTrung> kiemTraLichTrung(List<ChiTietLopHocPhan> listCTHP, List<LichHocSinhVien> listLH, int nhomTH) {
        Set<HocPhanTrung> hocPhanTrungs = new HashSet<>();
        listCTHP.forEach(chiTietLopHocPhan -> {
            listLH.forEach(lichHocSinhVien -> {
                if (kiemTraNgayBatDau(chiTietLopHocPhan.getNgayBatDau(), lichHocSinhVien.getChiTietLopHocPhan().getNgayBatDau(),
                        lichHocSinhVien.getChiTietLopHocPhan().getNgayKetThuc()) ||
                        chiTietLopHocPhan.getNhomTH() == nhomTH || chiTietLopHocPhan.getNhomTH() == 0) {
                    List<Integer> tietHoc1s = LichHocTheoTuanControler.extractNumbers(chiTietLopHocPhan.getTietHoc());
                    List<Integer> tietHoc2s = LichHocTheoTuanControler.extractNumbers(lichHocSinhVien.getChiTietLopHocPhan().getTietHoc());
                    if (tietHoc1s.get(0) == tietHoc2s.get(0)) {
                        if (tietHoc1s.get(1) >= tietHoc2s.get(1) && tietHoc1s.get(1) <= tietHoc2s.get(2))
                            hocPhanTrungs.add(new HocPhanTrung(lichHocSinhVien.getChiTietLopHocPhan().getLopHocPhan().getTenLHP(),
                                    lichHocSinhVien.getChiTietLopHocPhan().getTietHoc(),
                                    lichHocSinhVien.getChiTietLopHocPhan().getCoSo() + " " +
                                            lichHocSinhVien.getChiTietLopHocPhan().getPhong(),
                                    lichHocSinhVien.getChiTietLopHocPhan().getGiangVien().getTenGV(),
                                    convertToLocalDate(lichHocSinhVien.getChiTietLopHocPhan().getNgayBatDau()) +
                                            " : " + convertToLocalDate(lichHocSinhVien.getChiTietLopHocPhan().getNgayKetThuc())));
                        else {
                            if (tietHoc1s.get(2) >= tietHoc2s.get(1) && tietHoc1s.get(2) <= tietHoc2s.get(2))
                                hocPhanTrungs.add(new HocPhanTrung(lichHocSinhVien.getChiTietLopHocPhan().getLopHocPhan().getTenLHP(),
                                        lichHocSinhVien.getChiTietLopHocPhan().getTietHoc(),
                                        lichHocSinhVien.getChiTietLopHocPhan().getCoSo() + " " +
                                                lichHocSinhVien.getChiTietLopHocPhan().getPhong(),
                                        lichHocSinhVien.getChiTietLopHocPhan().getGiangVien().getTenGV(),
                                        convertToLocalDate(lichHocSinhVien.getChiTietLopHocPhan().getNgayBatDau()) +
                                                " : " + convertToLocalDate(lichHocSinhVien.getChiTietLopHocPhan().getNgayKetThuc())));
                        }
                    }
                }
            });
        });
        return hocPhanTrungs;
    }

    private boolean kiemTraNgayBatDau(Date ngayhoc, Date ngayBatDau, Date ngayKetThuc) {
        if (!ngayBatDau.after(ngayhoc) && !ngayKetThuc.before(ngayhoc))
            return true;
        return false;
    }

    @PostMapping("/xem-lop-hoc-phan")
    @ResponseBody
    public List<ThongTinLopHP> findThongTinLopByMaLHP(@RequestParam("maLHP") long maLHP, HttpSession session) {
        String maSV = (String) session.getAttribute("maSV");
        List<ThongTinLopHP> thongTinLopHPS = new ArrayList<>();
        List<LichHocSinhVien> listLH = lichHocSinhVienService.getLichHocByMaSV(maSV);
        ctlhpService.findByMaLopHocPhan(maLHP).forEach(chiTietLopHocPhan -> {
            if (kiemTraCTHPtrongLH(chiTietLopHocPhan.getMaCTLHP(), listLH)) {
                String nhomTH = "Lý thuyết";
                if (chiTietLopHocPhan.getNhomTH() > 0)
                    nhomTH = chiTietLopHocPhan.getNhomTH() + "";
                thongTinLopHPS.add(new ThongTinLopHP(chiTietLopHocPhan.getTietHoc(), nhomTH,
                        chiTietLopHocPhan.getPhong(), chiTietLopHocPhan.getDayNha(), chiTietLopHocPhan.getCoSo(),
                        chiTietLopHocPhan.getGiangVien().getTenGV(),
                        convertToLocalDate(chiTietLopHocPhan.getNgayBatDau()) + " : " + convertToLocalDate(chiTietLopHocPhan.getNgayKetThuc())));
            }
        });
        return thongTinLopHPS;
    }

    private boolean kiemTraCTHPtrongLH(long maCTLHP, List<LichHocSinhVien> listLH) {
        for (LichHocSinhVien lichHocSinhVien : listLH) {
            if (lichHocSinhVien.getChiTietLopHocPhan().getMaCTLHP() == maCTLHP)
                return true;
        }
        return false;
    }

    @GetMapping("/in-lich")
    public String InLHPDaDK(Model model, HttpSession session) {
        String maSV = (String) session.getAttribute("maSV");
        model.addAttribute("sinhvien", sinhVienService.findById(maSV));
        model.addAttribute("listHocPhanDaDK", list);
        model.addAttribute("HKLHP", hocKyLHPS(maSV));
        model.addAttribute("maHK", maHocKy);
        return "in-lop-hoc-phan";
    }
}

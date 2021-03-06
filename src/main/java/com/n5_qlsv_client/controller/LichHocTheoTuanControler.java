package com.n5_qlsv_client.controller;

import com.n5_qlsv_client.model.ItemLichHoc;
import com.n5_qlsv_client.model.LichHocSinhVien;
import com.n5_qlsv_client.model.LichHocTheoTienDo;
import com.n5_qlsv_client.model.SinhVien;
import com.n5_qlsv_client.service.LichHocSinhVienService;
import com.n5_qlsv_client.service.SinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/lich-theo-tuan")
public class LichHocTheoTuanControler {

    @Autowired
    private LichHocSinhVienService lichHocSinhVienService;
    
    @Autowired
    private SinhVienService sinhVienService;

    @GetMapping("/in-lich-tien-do")
    private String inLichHocTheoTienDo(Principal principal, Model model) {
        //Lấy mã sinh viên thông qua login principal
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        String maSV = loginedUser.getUsername();

        SinhVien sinhVien = sinhVienService.findById(maSV);
        model.addAttribute("tensinhvien", sinhVien.getTenSV());
        
        Set<LichHocTheoTienDo> lich = new HashSet<>();
        lichHocSinhVienService.getLichHocByMaSV(maSV).forEach(lichHocSinhVien -> {
            List<Integer> tietHocs = extractNumbers(lichHocSinhVien.getChiTietLopHocPhan().getTietHoc());
            LocalDate dateBD = lichHocSinhVien.getChiTietLopHocPhan().getNgayBatDau()
                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate dateKT = lichHocSinhVien.getChiTietLopHocPhan().getNgayKetThuc()
                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            lich.add(new LichHocTheoTienDo(lichHocSinhVien.getChiTietLopHocPhan().getLopHocPhan().getMaLHP() + "",
                    lichHocSinhVien.getChiTietLopHocPhan().getLopHocPhan().getTenLHP(),
                    lichHocSinhVien.getChiTietLopHocPhan().getLopHocPhan().getHocPhan().getSoTCLT() +
                            lichHocSinhVien.getChiTietLopHocPhan().getLopHocPhan().getHocPhan().getSoTCTH() + "",
                    tietHocs.get(0) + "", tietHocs.get(1) + " - " + tietHocs.get(2),
                    lichHocSinhVien.getChiTietLopHocPhan().getPhong(),
                    lichHocSinhVien.getChiTietLopHocPhan().getNhomTH() + "",
                    dateBD.toString(), dateKT.toString(),
                    lichHocSinhVien.getChiTietLopHocPhan().getGiangVien().getMaGV() + "",
                    lichHocSinhVien.getChiTietLopHocPhan().getGiangVien().getTenGV()));
        });

        model.addAttribute("lichTienDo", lich);
        return "in-lich-hoc-theo-tien-do";
    }

    @GetMapping("/lich-tien-do")
    private String listLichHocTheoTienDo(Principal principal, Model model) {

        //Lấy mã sinh viên thông qua login principal
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        String maSV = loginedUser.getUsername();
        SinhVien sinhVien = sinhVienService.findById(maSV);
        model.addAttribute("tensinhvien", sinhVien.getTenSV());

        Set<LichHocTheoTienDo> lich = new HashSet<>();
        lichHocSinhVienService.getLichHocByMaSV(maSV).forEach(lichHocSinhVien -> {
            List<Integer> tietHocs = extractNumbers(lichHocSinhVien.getChiTietLopHocPhan().getTietHoc());
            LocalDate dateBD = lichHocSinhVien.getChiTietLopHocPhan().getNgayBatDau()
                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate dateKT = lichHocSinhVien.getChiTietLopHocPhan().getNgayKetThuc()
                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            lich.add(new LichHocTheoTienDo(lichHocSinhVien.getChiTietLopHocPhan().getLopHocPhan().getMaLHP() + "",
                    lichHocSinhVien.getChiTietLopHocPhan().getLopHocPhan().getTenLHP(),
                    lichHocSinhVien.getChiTietLopHocPhan().getLopHocPhan().getHocPhan().getSoTCLT() +
                            lichHocSinhVien.getChiTietLopHocPhan().getLopHocPhan().getHocPhan().getSoTCTH() + "",
                    tietHocs.get(0) + "", tietHocs.get(1) + " - " + tietHocs.get(2),
                    lichHocSinhVien.getChiTietLopHocPhan().getPhong(),
                    lichHocSinhVien.getChiTietLopHocPhan().getNhomTH() + "",
                    dateBD.toString(), dateKT.toString(),
                    lichHocSinhVien.getChiTietLopHocPhan().getGiangVien().getMaGV() + "",
                    lichHocSinhVien.getChiTietLopHocPhan().getGiangVien().getTenGV()));
        });

        model.addAttribute("lichTienDo", lich);
        return "lich-theo-tien-do";
    }

    @GetMapping("/lich-hoc")
    @ResponseBody
    public List<ItemLichHoc> listLichHoc(Principal principal) {

        //Lấy mã sinh viên thông qua login principal
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        String maSV = loginedUser.getUsername();

        List<LichHocSinhVien> list = lichHocSinhVienService.getLichHocByMaSV(maSV);
        List<ItemLichHoc> lichHocs = new ArrayList<>();
        list.forEach(lichHocSinhVien -> {
            LocalDate dateBD = lichHocSinhVien.getChiTietLopHocPhan().getNgayBatDau()
                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate dateKT = lichHocSinhVien.getChiTietLopHocPhan().getNgayKetThuc()
                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            List<Integer> tietHocs = extractNumbers(lichHocSinhVien.getChiTietLopHocPhan().getTietHoc());
            DayOfWeek thu;
            switch (tietHocs.get(0)) {
                case 2:
                    thu = DayOfWeek.MONDAY;
                    break;
                case 3:
                    thu = DayOfWeek.TUESDAY;
                    break;
                case 4:
                    thu = DayOfWeek.WEDNESDAY;
                    break;
                case 5:
                    thu = DayOfWeek.THURSDAY;
                    break;
                case 6:
                    thu = DayOfWeek.FRIDAY;
                    break;
                case 7:
                    thu = DayOfWeek.SATURDAY;
                    break;
                default:
                    thu = DayOfWeek.SUNDAY;
                    break;
            }
            int tietBD = tietHocs.get(1);
            int tietKT = tietHocs.get(2);
            String title = lichHocSinhVien.getChiTietLopHocPhan().getLopHocPhan().getHocPhan().getMonHoc().getTenMonHoc().toUpperCase() +
                    "; Phòng: " + lichHocSinhVien.getChiTietLopHocPhan().getPhong() +
                    "; GV: " + lichHocSinhVien.getChiTietLopHocPhan().getGiangVien().getTenGV();
            for (LocalDate date = dateBD; date.isBefore(dateKT); date = date.plusDays(1)) {
                ItemLichHoc itemLichHoc = new ItemLichHoc();
                itemLichHoc.setTitle(title);
                if (date.getDayOfWeek() == thu) {
                    itemLichHoc.setStart(chuyenTietBDThanhTime(date, tietBD));
                    itemLichHoc.setEnd(chuyenTietKTThanhTime(date, tietKT));
                    lichHocs.add(itemLichHoc);
                }
            }
        });
        return lichHocs;
    }

    @GetMapping
    public String LichTheoTuan(Model model) {
        model.addAttribute("TrangHienTai", "Lịch Theo Tuần");
        return "lich-theo-tuan";
    }

    @GetMapping("/in-lich")
    public String InLichTheoTuan(@RequestParam("date") String date, Model model) {
        model.addAttribute("date", date);
        return "in-lich-sinh-vien";
    }

    public static List<Integer> extractNumbers(String s) {
        List<Integer> numbers = new ArrayList<Integer>();

        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(s);

        while (m.find()) {
            numbers.add(Integer.parseInt(m.group()));
        }
        return numbers;
    }

    private LocalDateTime chuyenTietBDThanhTime(LocalDate date, int tiet) {
        LocalDateTime localDateTime;
        switch (tiet) {
            case 1:
                localDateTime = date.atTime(6, 30, 0);
                break;
            case 2:
                localDateTime = date.atTime(7, 20, 0);
                break;
            case 3:
                localDateTime = date.atTime(8, 10, 0);
                break;
            case 4:
                localDateTime = date.atTime(9, 10, 0);
                break;
            case 5:
                localDateTime = date.atTime(10, 0, 0);
                break;
            case 6:
                localDateTime = date.atTime(10, 50, 0);
                break;
            case 7:
                localDateTime = date.atTime(12, 30, 0);
                break;
            case 8:
                localDateTime = date.atTime(13, 20, 0);
                break;
            case 9:
                localDateTime = date.atTime(14, 10, 0);
                break;
            case 10:
                localDateTime = date.atTime(15, 10, 0);
                break;
            case 11:
                localDateTime = date.atTime(16, 0, 0);
                break;
            case 12:
                localDateTime = date.atTime(16, 50, 0);
                break;
            case 13:
                localDateTime = date.atTime(18, 0, 0);
                break;
            case 14:
                localDateTime = date.atTime(18, 50, 0);
                break;
            case 15:
                localDateTime = date.atTime(19, 50, 0);
                break;
            case 16:
                localDateTime = date.atTime(20, 40, 0);
                break;
            default:
                localDateTime = null;
                break;
        }
        return localDateTime;
    }

    private LocalDateTime chuyenTietKTThanhTime(LocalDate date, int tiet) {
        LocalDateTime localDateTime;
        switch (tiet) {
            case 1:
                localDateTime = date.atTime(7, 20, 0);
                break;
            case 2:
                localDateTime = date.atTime(8, 10, 0);
                break;
            case 3:
                localDateTime = date.atTime(9, 0, 0);
                break;
            case 4:
                localDateTime = date.atTime(10, 0, 0);
                break;
            case 5:
                localDateTime = date.atTime(10, 50, 0);
                break;
            case 6:
                localDateTime = date.atTime(11, 40, 0);
                break;
            case 7:
                localDateTime = date.atTime(13, 20, 0);
                break;
            case 8:
                localDateTime = date.atTime(14, 10, 0);
                break;
            case 9:
                localDateTime = date.atTime(15, 0, 0);
                break;
            case 10:
                localDateTime = date.atTime(16, 0, 0);
                break;
            case 11:
                localDateTime = date.atTime(16, 50, 0);
                break;
            case 12:
                localDateTime = date.atTime(17, 40, 0);
                break;
            case 13:
                localDateTime = date.atTime(18, 50, 0);
                break;
            case 14:
                localDateTime = date.atTime(19, 40, 0);
                break;
            case 15:
                localDateTime = date.atTime(20, 40, 0);
                break;
            case 16:
                localDateTime = date.atTime(21, 30, 0);
                break;
            default:
                localDateTime = null;
                break;
        }
        return localDateTime;
    }
}

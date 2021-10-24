package com.n5_qlsv_client.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SinhVien {

    private long maSV;

    private String tenSV;

    private String email;

    private boolean gioiTinh;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ngaySinh;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ngayVaoTruong;

    private String soDienThoai;

    private String soCMND;

    private String bacDaoTao;

    private String diaChi;

    private String roleName;

    private String password;

    private Khoa khoa;

    private LopHoc lopHoc;

    private ChuyenNganh chuyenNganh;

    private List<KetQuaHocTap> ketQuaHocTapList;

    private List<LichHocSinhVien> lichHocSinhVienList;
}
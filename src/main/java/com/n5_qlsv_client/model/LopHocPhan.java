package com.n5_qlsv_client.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LopHocPhan {

    private long maLHP;

    private String tenLHP;

    private String moTa;

    private String tenVietTat;

    private String trangThai;

    private int soNhomTH;

    private int soLuongDangKyHienTai;

    private int soLuongDangKyToiDa;

    private HocKy hocKy;

    private HocPhan hocPhan;

    private List<ChiTietLopHocPhan> chiTietLopHocPhanList;

    private List<KetQuaHocTap> ketQuaHocTapList;

    private List<LichHoc> lichHocList;
}

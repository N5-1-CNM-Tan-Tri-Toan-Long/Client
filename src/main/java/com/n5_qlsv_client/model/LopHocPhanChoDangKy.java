package com.n5_qlsv_client.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LopHocPhanChoDangKy {
    long maLopHocPhan;
    String tenLop, trangThai , tenVietTat;
    int soLuongDangKyHienTai, soLuongDangKyToiDa;
}

package com.n5_qlsv_client.model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LichHocSinhVien {

    private long maLHSV;

    private ChiTietLopHocPhan chiTietLopHocPhan;

    private SinhVien sinhVien;

    private Date ngayDangKyHP;
}

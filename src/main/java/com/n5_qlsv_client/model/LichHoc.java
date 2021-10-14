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
public class LichHoc {

    private long maLichHoc;

    private String phong;

    private String soTiet;

    private String tenGV;

    private String ghiChu;

    private LopHocPhan lopHocPhan;

    private List<LichHocSinhVien> lichHocSinhVienList;
}

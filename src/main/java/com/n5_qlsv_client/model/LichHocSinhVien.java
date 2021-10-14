package com.n5_qlsv_client.model;

import lombok.*;

@Getter
@Setter
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LichHocSinhVien {

    private long maLHSV;

    private LichHoc lichHoc;

    private SinhVien sinhVien;
}

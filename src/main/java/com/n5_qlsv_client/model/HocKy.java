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
public class HocKy {

    private long maHK;

    private String moTa;

    private int namBatDau;

    private int namKetThuc;

    private int thuTuHocKy;

    private List<LopHocPhan> lopHocPhanList;
}

package com.n5_qlsv_client.model;

import lombok.*;

@Getter
@Setter
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HocKyLHP {

    private long maHK;

    private String moTa;

    private int namBatDau;

    private int namKetThuc;

    private int thuTuHocKy;
}

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
public class LopHoc {

    private long maLop;

    private String tenLop;

    private List<SinhVien> sinhVienList;
}

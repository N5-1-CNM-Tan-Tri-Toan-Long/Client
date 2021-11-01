package com.n5_qlsv_client.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
public class ItemLichHoc {
    String title;
    LocalDateTime start, end;
}

package com.example.fastcampusmysql.domain.post.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


/**
 *  포매터 설정을 해줘야 함
 */
public record DailyPostCountRequest(
        Long memberId,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate firstDate,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate lastDate
) {
}

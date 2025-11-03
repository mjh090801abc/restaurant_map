package com.yummap.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // 맛집 이름
    private String area; // 지역/주소
    private String memo;
    private double lat; // 위도
    private double lng; // 경도
    private LocalDateTime dateSaved;

    // 생성시간 설정
    @PrePersist
    protected void onCreate() {
        if (dateSaved == null) {
            dateSaved = LocalDateTime.now();
        }
    }
}


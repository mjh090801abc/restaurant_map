package com.yummap.app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class RestaurantRequestDto {

    // NotBlank : null, 공백 문자열 모두 허용
    @NotBlank(message = "맛집 이름 필수 입력 항목입니다.")
    private String name;

    @NotBlank(message = "지역/주소는 필수 입력 항목입니다.")
    private String area;

    @NotBlank(message = "위도 입력은 필수 입력 항목입니다.")
    private double lat;

    @NotBlank(message = "경도 입력은 필수 입력 항목입니다.")
    private double lng;

    private String memo;
}

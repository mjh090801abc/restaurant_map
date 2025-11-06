package com.yummap.app.service;

import com.yummap.app.dto.RestaurantRequestDto;
import com.yummap.app.entity.Restaurant;
import com.yummap.app.repository.RestaurantRepository;
// import jakarta.transaction.Transactional; 이 import문은 readOnly를 지원하지 않음
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Service 코드 (Import 제외)

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository; // Repository 주입 (DB와의 통신 담당)

    // 1. 모든 맛집 조회
    @Transactional(readOnly = true)
    public List<Restaurant> findAllRestaurants() { // List<Restaurant> : List<> 결과는 0 ~ 여러 객채를 담음
        // Repository의 기본 기능을 호출하여 DB에서 모든 데이터를 가져오기
        return restaurantRepository.findAll();
    }

    // 2. 특정 맛집 ID로 조회
    @Transactional(readOnly = true)
    public Optional<Restaurant> findRestaurantById(Long id) { // Optional<Restaurant> : Optional<> 고유 번호(id) 같은것으로 구분하여 객체를 담음 (결과는 0, 1개)
        // Optional<T>는 값이 존재할 수도, 없을 수도 있는 객체를 안전하게 다루게 해줌
        return restaurantRepository.findById(id);
    }

    // 3. 맛집 저장 (생성)
    @Transactional
    public Restaurant saveNewRestaurant(RestaurantRequestDto dto) {
        Restaurant restaurant = new Restaurant();

        // DTO 데이터를 Entity 객체에 매핑
        restaurant.setName(dto.getName());
    }


}

package com.yummap.app.service;

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
    public List<Restaurant> findAllRestaurants() {
        // Repository의 기본 기능을 호출하여 DB에서 모든 데이터를 가져옵니다.
        return restaurantRepository.findAll();
    }

    // 2. 특정 맛집 ID로 조회
    @Transactional(readOnly = true)
    public Optional<Restaurant> findRestaurantById(Long id) {
        // Optional<T>는 값이 존재할 수도, 없을 수도 있는 객체를 안전하게 다루게 해줍니다.
        return restaurantRepository.findById(id);
    }
}

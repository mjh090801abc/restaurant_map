package com.yummap.app.controller;

import com.yummap.app.entity.Restaurant;
import com.yummap.app.repository.RestaurantRepository;
import com.yummap.app.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    // 맛집 조회
    @GetMapping
    public List<Restaurant> getAllRestaurants() {
        // Service 계층의 비즈니스 로직 호출
        // 반환된 List<Restaurant> 객체는 @RestController에 의해 JSON 배열로 변환되어 클라이언트에 응답
        return restaurantService.findAllRestaurants();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurant(@PathVariable Long id) {
        try {

        }
    }


}

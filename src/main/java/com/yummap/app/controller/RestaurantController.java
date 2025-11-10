package com.yummap.app.controller;

import com.yummap.app.entity.Restaurant;
import com.yummap.app.repository.RestaurantRepository;
import com.yummap.app.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping
    public List<Restaurant> getAllRestaurants() {
        // Service 계층의 비즈니스 로직 호출
    }


}

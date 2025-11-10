package com.yummap.app.controller;

import com.yummap.app.entity.Restaurant;
import com.yummap.app.repository.RestaurantRepository;
import com.yummap.app.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

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
    // @PathVariable Long id: URL 경로의 {id} 값을 추출하여 Long 타입의 id 변수에 대입
    public ResponseEntity<Restaurant> getRestaurant(@PathVariable Long id) {
        try {
            // Service에게 ID로 데이터를 찾아달라고 요청 결과는 Optional<Restaurant>입니다.
            Restaurant restaurant = restaurantService.findRestaurantById(id)
                    // orElseThrow(): Optional 안에 값이 없으면(맛집이 없으면) 즉시 예외(NoSuchElementException)를 발생시킵니다.
                    .orElseThrow(() -> new NoSuchElementException("맛집 ID를 찾을 수 없습니다: " + id));

            // 조회 성공 시 http 상태 코드 200ok와 함께 Entity(JSON) 반환
            return ResponseEntity.ok(restaurant);
        } catch (NoSuchElementException e) {
            // NoSuchElementException 발생 시: HTTP 상태 코드 404 Not Found를 반환하고 응답 본문은 비움
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
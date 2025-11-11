package com.yummap.app.controller;

import com.yummap.app.dto.RestaurantRequestDto;
import com.yummap.app.entity.Restaurant;
import com.yummap.app.service.RestaurantService;
import jakarta.validation.Valid;
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

    // 맛집 상세 정보 조회
    @GetMapping("/{id}")
    // @PathVariable : 경로 변수이며 웹 주소(URL)의 특정 부분이 데이터(변수)암을 spring에게 알림
    // @PathVariable Long id: URL 경로의 {id} 값을 추출하여 Long 타입의 id 변수에 대입
    // ResponseEntity는 만든적이 없지만 spring framework에서 기본적으로 제공하는 표준 클래스이 HTTP의 응답을 나타내기 위한 클래스이 200ok, 404NotFound 등 응답의 결과를 나타냄
    public ResponseEntity<Restaurant> getRestaurant(@PathVariable Long id) {
        try {
            // Service에게 ID로 데이터를 찾아달라고 요청 결과는 Optional<Restaurant>
            Restaurant restaurant = restaurantService.findRestaurantById(id)
                    // orElseThrow(): Optional 안에 값이 없으면(맛집이 없으면) 즉시 예외(NoSuchElementException)를 발생시킵니다.
                    // if else 라고 생각 (estaurant restaurant = restaurantService.findRestaurantById(id)가 if문 아래가 else)
                    .orElseThrow(() -> new NoSuchElementException("맛집 ID를 찾을 수 없습니다: " + id));

            // 조회 성공 시 http 상태 코드 200ok와 함께 Entity(JSON) 반환
            return ResponseEntity.ok(restaurant);
            // e : exception(예외)
            // catch (예외_타입 변수명)
        } catch (NoSuchElementException e) {
            // NoSuchElementException 발생 시: HTTP 상태 코드 404 Not Found를 반환하고 응답 본문은 비움
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // 맛집 생성
    @PostMapping
    // @RequestBody : 클라이언트가 보낸 JSON본문을 DTO객체로 자동 변환해 담음
    // @Valid : DTO에 정의된 유효성 검사(@NotBlank 등)를 이 시점에서 즉시 수행
    public ResponseEntity<Restaurant> createRestaurant(@Valid @RequestBody RestaurantRequestDto requestDto) {
        // Service에게 DTO를 넘겨 주어 저장 요청 (Service는 Entity로 변환 후 저장)
        Restaurant restaurant = restaurantService.saveNewRestaurant(requestDto);

        // 저장 성공 시 HTTP 상태 코드 201 Created와 함께 저장된 Entity(JSON)를 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurant);
    }

    // 맛집 삭제
    // @DeleteMapping : http에게 delete 요청
    @DeleteMapping("/{id}")
    // Void는 값이 없다는 것을 의미
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        // service에게 id로 삭제 요청
        restaurantService.deleteRestaurant(id);

        // 삭제 완료 시 http 상태 코드 204 noContent()를 반환
        // http가 delete 요청 -> 서버가 성공적으로 삭제 -> 삭제 후 돌려줄 새로운 데이터는 없음
        return ResponseEntity.noContent().build();
    }

    // 유효성 검사 실패 시 처리
    @ResponseStatus(HttpStatus.BAD_REQUEST)




}
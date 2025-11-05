package com.yummap.app.repository;

import com.yummap.app.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    // JpaRepository<T, ID> : T는 entity 클래스, ID는 기본키 타입
}

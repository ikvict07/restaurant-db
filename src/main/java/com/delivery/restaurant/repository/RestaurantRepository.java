package com.delivery.restaurant.repository;

import com.delivery.restaurant.Enums.RestaurantStatus;
import com.delivery.restaurant.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> getRestaurantByAddress(String address);
    Optional<Restaurant> getRestaurantByStatus(RestaurantStatus status);
    @Query("SELECT r FROM Restaurant r WHERE r.email_to_login = ?1")
    Optional<Restaurant> findByEmail_to_login(String email_to_login);
    Optional<Restaurant> getRestaurantById(Long id);
    @Query("SELECT r FROM Restaurant r ORDER BY r.id")
    List<Restaurant> getAllRestaurants();

}

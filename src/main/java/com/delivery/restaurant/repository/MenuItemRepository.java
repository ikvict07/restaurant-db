package com.delivery.restaurant.repository;

import com.delivery.restaurant.model.MenuItem;
import com.delivery.restaurant.model.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long>{
    List<MenuItem> getMenuItemsByRestaurant(Restaurant restaurant);
    Optional<MenuItem> getMenuItemByName(String name);
    Optional<MenuItem> getMenuItemByNameAndRestaurant(String name, Restaurant restaurant);
    @Query("SELECT m FROM MenuItem m ORDER BY m.restaurant.id")
    List<MenuItem> getAllMenuItems();

    @Query("SELECT m FROM MenuItem m WHERE m.restaurant.id = ?1")
    Page<MenuItem> getPaginatedMenuItemsByRestaurant(Restaurant restaurant, Pageable pageable);
}

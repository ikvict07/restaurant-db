package com.delivery.restaurant.controller;

import com.delivery.restaurant.DTO.CreateMenuItem;
import com.delivery.restaurant.model.MenuItem;
import com.delivery.restaurant.model.Restaurant;
import com.delivery.restaurant.repository.MenuItemRepository;
import com.delivery.restaurant.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/restaurant/menu/")
public class MenuController {

    private static final int MAXIMUM_PAGE_SIZE = 100;
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    @Autowired
    public MenuController(MenuItemRepository menuItemRepository, RestaurantRepository restaurantRepository) {
        this.menuItemRepository = menuItemRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping("/full-list")
    public ResponseEntity<List<MenuItem>> getMenuItems() {
        List<MenuItem> menuItems = menuItemRepository.getAllMenuItems();
        return ResponseEntity.ok(menuItems);
    }
    @GetMapping("/list-by-restaurant")
    public ResponseEntity<List<MenuItem>> getMenuItems(@RequestParam("id") Long id) {
        Optional<Restaurant> restaurant = restaurantRepository.getRestaurantById(id);
        if (restaurant.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<MenuItem> menuItems = menuItemRepository.getMenuItemsByRestaurant(restaurant.get());
        return ResponseEntity.ok(menuItems);
    }

    @GetMapping("/paginated-list-by-restaurant")
    public ResponseEntity<List<MenuItem>> getPaginatedMenuItems(@RequestParam("id") Long id,
                                                                @RequestParam("start") int size_of_page,
                                                                @RequestParam("stop") int page_num) {
        Optional<Restaurant> restaurant = restaurantRepository.getRestaurantById(id);
        if (restaurant.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (size_of_page > MAXIMUM_PAGE_SIZE) {
            return ResponseEntity.badRequest().build();
        }

        PageRequest pageable = PageRequest.of(page_num, size_of_page);
        Page<MenuItem> menuItemsPage = menuItemRepository.getPaginatedMenuItemsByRestaurant(restaurant.get(), pageable);

        List<MenuItem> menuItems = menuItemsPage.getContent();
        return ResponseEntity.ok(menuItems);
    }

    @PostMapping("/create-item")
    public ResponseEntity<?> createMenuItem(@RequestBody CreateMenuItem menuItem) {
        if (menuItemRepository.getMenuItemByName(menuItem.getName()).isPresent()){
            return ResponseEntity.badRequest().body("Menu item with this name already exists");
        }

        menuItemRepository.save(new MenuItem(menuItem));
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/delete-item")
    public ResponseEntity<?> deleteMenuItem(@RequestParam("id") Long id) {
        Optional<MenuItem> menuItem = menuItemRepository.findById(id);
        if (menuItem.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        menuItemRepository.delete(menuItem.get());
        return ResponseEntity.ok().build();
    }
    @PutMapping("/update-item")
    public ResponseEntity<?> updateMenuItem(MenuItem updatedMenuItem) {
        Optional<MenuItem> menuItem = menuItemRepository.findById(updatedMenuItem.getId());
        if (menuItem.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        menuItemRepository.save(menuItem.get());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-item")
    public ResponseEntity<MenuItem> getMenuItem(@RequestParam("restaurant-id") Long restaurantId, @RequestParam("item-name") String itemName) {
        Optional<Restaurant> restaurant = restaurantRepository.getRestaurantById(restaurantId);
        if (restaurant.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<MenuItem> menuItem = menuItemRepository.getMenuItemByNameAndRestaurant(itemName, restaurant.get());
        return menuItem.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }
}

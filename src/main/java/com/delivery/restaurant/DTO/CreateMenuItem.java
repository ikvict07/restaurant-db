package com.delivery.restaurant.DTO;

import com.delivery.restaurant.model.Restaurant;
import lombok.Data;

@Data
public class CreateMenuItem {
    private Restaurant restaurant;
    private String name;
    private double price;
    private String url;
    private String description;
}

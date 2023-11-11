package com.delivery.restaurant.model;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String name;
    double price;
    String url;
    String description;
}

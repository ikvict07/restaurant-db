package com.delivery.restaurant.model;

import com.delivery.restaurant.Enums.RestaurantStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String address;
    private RestaurantStatus status;

    private final String role = "RESTAURANT";
    private String email_to_login;
    private String password;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<MenuItem> menuItems;
}

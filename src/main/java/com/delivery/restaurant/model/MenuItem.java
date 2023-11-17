package com.delivery.restaurant.model;

import com.delivery.restaurant.DTO.CreateMenuItem;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    @ToString.Exclude
    private Restaurant restaurant;
    private String name;
    private double price;
    private String url;
    private String description;

    public MenuItem(Restaurant restaurant, String name, double price, String url, String description) {
        this.name = name;
        this.price = price;
        this.url = url;
        this.restaurant = restaurant;
        this.description = description;
    }
    public MenuItem(CreateMenuItem menuItem) {
        this.name = menuItem.getName();
        this.price = menuItem.getPrice();
        this.url = menuItem.getUrl();
        this.restaurant = menuItem.getRestaurant();
        this.description = menuItem.getDescription();
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        MenuItem menuItem = (MenuItem) o;
        return getId() != null && Objects.equals(getId(), menuItem.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}

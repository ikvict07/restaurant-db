package com.delivery.restaurant.controller;

import com.delivery.restaurant.DTO.SignInRequest;
import com.delivery.restaurant.jwt.JwtCore;
import com.delivery.restaurant.model.Restaurant;
import com.delivery.restaurant.repository.RestaurantRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/api/restaurant/auth/")
public class AuthController {
    private final JwtCore jwtCore;
    private final RestaurantRepository restaurantRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public AuthController(JwtCore jwtCore, RestaurantRepository restaurantRepository, PasswordEncoder passwordEncoder) {
        this.jwtCore = jwtCore;
        this.restaurantRepository = restaurantRepository;
        this.passwordEncoder = passwordEncoder;
    }

//    @RequestMapping("/login")
//    private ResponseEntity<String> login(@RequestBody SignInRequest signInRequest, HttpServletRequest request) {
//        if (!isRequestHavingPermission(request)) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You don`t have permissions");
//        }
//        String jwt = resolveToken(request);
//        if (jwt == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You have to be authorized");
//        }
//
//
//        Optional<Restaurant> optionalRestaurant = restaurantRepository.getRestaurantByEmail_to_login(signInRequest.getEmail());
//        if (optionalRestaurant.isPresent() && passwordEncoder.matches(signInRequest.getPassword(), optionalRestaurant.get().getPassword())) {
//
//            if (optionalRestaurant.get().getRole().equals("RESTAURANT")) {
//                return ResponseEntity.ok().body("Success");
//            } else {
//                return ResponseEntity.badRequest().body("Only restaurants can login");
//            }
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username or password");
//        }
//    }
    /**
     * Checks if the request has the required permission.
     *
     * @param request The HTTP request object.
     * @return true if the request has the required permission, false otherwise.
     */
    private boolean isRequestHavingPermission(HttpServletRequest request) {
        String jwt = resolveToken(request);
        if (jwt == null) {
            return false;
        }
        return (Objects.equals(jwtCore.getKeyFromJwt(jwt), "Allowed"));
    }

    /**
     * Resolves the bearer token from the Authorization header of the HTTP request.
     *
     * @param request The HTTP request object.
     * @return The bearer token if found, null otherwise.
     */
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}

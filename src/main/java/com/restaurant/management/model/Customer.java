package com.restaurant.management.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long customerId;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    List<Order> orders;

    String firstName;
    String lastName;
    String email;
    String phone;
    String address;
    String dateOfBirth;
    String password;
}

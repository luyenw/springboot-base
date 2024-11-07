package com.canifa.stylenest.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "order_date")
    private LocalDateTime orderDate;
    @Column(name = "total_price")
    private Long totalPrice;
    private String status;
    private String fullname;
    private String address;
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
}

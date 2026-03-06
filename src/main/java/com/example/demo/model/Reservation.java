package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;
    private int partySize;
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private RestaurantTable table;

}

package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Data
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;
    private int partySize;
    private LocalDateTime dateTime;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "table_id")
    private RestaurantTable table;

}

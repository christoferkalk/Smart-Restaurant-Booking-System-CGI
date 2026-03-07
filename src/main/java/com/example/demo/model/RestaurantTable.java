package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Data
@Table(name = "restaurant_tables")
public class RestaurantTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int capacity;
    private String zone;
    private boolean windowSeat;
    private boolean quietCorner;
    private boolean accessible;
    private int xPosition;
    private int yPosition;

    @JsonManagedReference
    @OneToMany(mappedBy = "table", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

}

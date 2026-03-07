package com.example.demo.service;

import com.example.demo.model.RestaurantTable;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.RestaurantTableRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantTableService {

    private final ReservationRepository reservationRepository;
    private final RestaurantTableRepository tableRepository;

    public RestaurantTableService(RestaurantTableRepository tableRepository,
            ReservationRepository reservationRepository) {
        this.tableRepository = tableRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<RestaurantTable> getAllTables() {
        return tableRepository.findAll();
    }

    public RestaurantTable saveTable(RestaurantTable table) {
        return tableRepository.save(table);
    }

    public List<RestaurantTable> getAvailableTables(LocalDateTime dateTime) {
        List<RestaurantTable> allTables = tableRepository.findAll();
        LocalDateTime start = dateTime.minusHours(2);
        LocalDateTime end = dateTime.plusHours(2);

        return allTables.stream()
                .filter(table -> reservationRepository
                        .findByTableAndDateTimeBetween(table.getId(), start, end)
                        .isEmpty())
                .collect(Collectors.toList());
    }

}

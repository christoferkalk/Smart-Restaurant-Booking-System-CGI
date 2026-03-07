package com.example.demo.service;

import com.example.demo.model.RestaurantTable;
import com.example.demo.repository.RestaurantTableRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RestaurantTableService {

    private final RestaurantTableRepository tableRepository;

    public RestaurantTableService(RestaurantTableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    public List<RestaurantTable> getAllTables() {
        return tableRepository.findAll();
    }

    public RestaurantTable saveTable(RestaurantTable table) {
        return tableRepository.save(table);
    }

}

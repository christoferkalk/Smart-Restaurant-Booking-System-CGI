package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import com.example.demo.model.RestaurantTable;
import com.example.demo.service.RestaurantTableService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/tables")
public class RestaurantTableController {

    private final RestaurantTableService tableService;

    public RestaurantTableController(RestaurantTableService tableService) {
        this.tableService = tableService;
    }

    @GetMapping
    public List<RestaurantTable> getAllTables() {
        return tableService.getAllTables();
    }

    @PostMapping
    public RestaurantTable createTable(@RequestBody RestaurantTable table) {
        return tableService.saveTable(table);
    }

    @GetMapping("/available")
    public List<RestaurantTable> getAvailableTables(@RequestParam String dateTime) {
        LocalDateTime parsedDateTime = LocalDateTime.parse(dateTime);
        return tableService.getAvailableTables(parsedDateTime);
    }
}

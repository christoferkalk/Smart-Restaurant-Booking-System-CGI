package com.example.demo;

import com.example.demo.model.Reservation;
import com.example.demo.model.RestaurantTable;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.RestaurantTableRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RestaurantTableRepository tableRepository;
    private final ReservationRepository reservationRepository;

    public DataInitializer(RestaurantTableRepository tableRepository,
            ReservationRepository reservationRepository) {
        this.tableRepository = tableRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public void run(String... args) {
        if (tableRepository.count() > 0)
            return;

        List<RestaurantTable> tables = List.of(
                createTable(2, "Aken", true, false, false, 1, 1),
                createTable(2, "Aken", true, false, false, 1, 2),
                createTable(4, "Sisesaal", false, false, false, 2, 1),
                createTable(4, "Sisesaal", false, false, false, 2, 2),
                createTable(4, "Terrass", false, false, true, 3, 1),
                createTable(6, "Sisesaal", false, true, false, 4, 1),
                createTable(6, "Privaatsaal", false, true, false, 4, 2),
                createTable(8, "Sisesaal", false, false, false, 5, 1));

        tableRepository.saveAll(tables);

        Random random = new Random();
        for (RestaurantTable table : tableRepository.findAll()) {
            if (random.nextBoolean()) {
                Reservation reservation = new Reservation();
                reservation.setCustomerName("Klient " + random.nextInt(100));
                reservation.setPartySize(random.nextInt(table.getCapacity()) + 1);
                int hour = 11 + random.nextInt(11);
                reservation.setDateTime(LocalDateTime.now().plusDays(random.nextInt(3)).withHour(hour).withMinute(0));
                reservation.setTable(table);
                reservationRepository.save(reservation);
            }
        }
    }

    private RestaurantTable createTable(int capacity, String zone,
            boolean windowSeat, boolean quietCorner,
            boolean accessible, int x, int y) {
        RestaurantTable table = new RestaurantTable();
        table.setCapacity(capacity);
        table.setZone(zone);
        table.setWindowSeat(windowSeat);
        table.setQuietCorner(quietCorner);
        table.setAccessible(accessible);
        table.setXPosition(x);
        table.setYPosition(y);
        return table;
    }
}
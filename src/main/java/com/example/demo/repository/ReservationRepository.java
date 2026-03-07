package com.example.demo.repository;

import com.example.demo.model.Reservation;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT r FROM Reservation r WHERE r.table.id = :tableId AND r.dateTime < :end AND r.dateTime > :start")
    List<Reservation> findByTableAndDateTimeBetween(
            @Param("tableId") Long tableId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);

}

package com.example.reservation_system.reservation.availability;

import com.example.reservation_system.reservation.ReservationRepository;
import com.example.reservation_system.reservation.ReservationStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class ReservationAvailabilityService {

    private ReservationRepository repository;

    public ReservationAvailabilityService(ReservationRepository repository) {
        this.repository = repository;
    }

    public boolean isReservationAvailable(
            Long roomId,
            LocalDate startDate,
            LocalDate endDate) {

        if (!endDate.isAfter(startDate)) {
            throw new IllegalArgumentException("Start date must be 1 day earlier than end date");
        }

        List<Long> conflict = repository.findConflictReservationIds(
                roomId,
                startDate,
                endDate,
                ReservationStatus.APPROVED);
        if (conflict.isEmpty()) {
            return true;
        }
        log.info("Conflict with ids ={}", conflict);
        return false;
    }
}

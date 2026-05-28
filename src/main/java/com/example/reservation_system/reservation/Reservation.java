package com.example.reservation_system.reservation;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.time.LocalDate;

public record Reservation(

        @Null
        Long id,

        @NotNull
        Long userId,

        @NotNull
        Long roomId,

        @FutureOrPresent(message = "Due date must be in the future or present")
        @NotNull(message = "Due date is required")
        LocalDate startDate,

        @FutureOrPresent(message = "Due date must be in the future or present")
        @NotNull(message = "Due date is required")
        LocalDate endDate,

        ReservationStatus status) {
}

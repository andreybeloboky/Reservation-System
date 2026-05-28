package com.example.reservation_system.reservation.availability;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CheckAvailabilityResponse(
        String message,
        AvailabilityStatus status) {

}

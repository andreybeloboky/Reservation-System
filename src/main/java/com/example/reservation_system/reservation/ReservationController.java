package com.example.reservation_system.reservation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
@Slf4j
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservation/{id}")
    public ResponseEntity<Reservation> getReservationByID(@PathVariable("id") Long id) {
        log.info("Called getReservationById: id=" + id);
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.getReservationById(id));
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservation() {
        log.info("Called getAllReservation");
        return ResponseEntity.ok(reservationService.findAllReservation());

    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservationToCreate) {
        log.info("Called createReservation");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reservationService.createReservation(reservationToCreate));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(
            @PathVariable("id") Long id,
            @RequestBody Reservation reservationToUpdate
    ) {
        log.info("Called method updateReservation id={}, reservationToUpdate={}", id, reservationToUpdate);
        Reservation updated = reservationService.updateReservation(id, reservationToUpdate);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<Void> deleteReservation(
            @PathVariable("id") Long id
    ) {
        log.info("Called deleteReservation: id={}", id);
        reservationService.cancelReservation(id);
        return ResponseEntity.ok().build();

    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<Reservation> approveReservation(
            @PathVariable("id") Long id
    ) {
        log.info("Called approveReservation: id={}", id);
        Reservation reservation = reservationService.approveReservation(id);
        return ResponseEntity.ok(reservation);
    }
}

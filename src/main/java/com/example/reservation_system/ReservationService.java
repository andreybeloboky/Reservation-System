package com.example.reservation_system;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ReservationService {

  /*  private final Map<Long, Reservation> reservationMap = Map.of(
            1L, new Reservation(1L, 100L, 40L, LocalDate.now(),
                    LocalDate.now().plusDays(5), ReservationStatus.APPROVED),
            2L, new Reservation(2L, 100L, 40L, LocalDate.now(),
                    LocalDate.now().plusDays(5), ReservationStatus.APPROVED)
    );

   */

    private final Map<Long, Reservation> reservationMap;
    private final AtomicLong idCounter;

    public ReservationService(Map<Long, Reservation> reservationMap) {
        this.reservationMap = reservationMap;
        idCounter = new AtomicLong();
    }

    public Reservation getReservationById(Long id) {
        if (!reservationMap.containsKey(id)) {
            throw new NoSuchElementException("Not found reservation by id = " + id);
        }
        return reservationMap.get(id);
    }

    public List<Reservation> findAllReservation() {
        return reservationMap.values().stream().toList();
    }

    public Reservation createReservation(Reservation reservationToCreate) {
        if (reservationToCreate.id() != null) {
            throw new IllegalArgumentException("Id should be empty");
        }
        if (reservationToCreate.status() != null) {
            throw new IllegalArgumentException("Status should be empty");
        }
        Reservation newReservation = new Reservation(
                idCounter.incrementAndGet(), reservationToCreate.userId(),
                reservationToCreate.roomId(), reservationToCreate.startDate(),
                reservationToCreate.endDate(), ReservationStatus.PENDING
        );
        reservationMap.put(newReservation.id(), newReservation);
        return newReservation;
    }

    public Reservation updateReservation(Long id, Reservation reservationToUpdate) {
        if(reservationMap.containsKey(id)){
            throw new NoSuchElementException("Not found reservation by id = " + id);
        }

        Reservation reservation = reservationMap.get(id);
        if(reservation.status()!= ReservationStatus.PENDING){
            throw new IllegalStateException("Cannot modify reservation: status =" + reservation.status());
        }

        Reservation updateReservation = new Reservation(
                reservation.id(),
                reservationToUpdate.userId(),
                reservationToUpdate.roomId(),
                reservationToUpdate.startDate(),
                reservationToUpdate.endDate(),
                ReservationStatus.PENDING
        );
        reservationMap.put(reservation.id(), updateReservation);
        return updateReservation;
    }

    public void deleteReservation(Long id) {
        if(reservationMap.containsKey(id)){
            throw new NoSuchElementException("Not found reservation by id = " + id);
        }
        reservationMap.remove(id);
    }

    public Reservation approveReservation(Long id) {
        if(!reservationMap.containsKey(id)){
            throw new IllegalStateException("Not found reservation by id = " + id);
        }
        Reservation reservation = reservationMap.get(id);
        if(reservation.status() != ReservationStatus.PENDING){
            throw new IllegalStateException("Cannot approve reservation status= " + reservation.status());
        }
        boolean isConflict = isReservationConflict(reservation);
        if(isConflict){
            throw new IllegalStateException("Cannot approve reservation because of conflict");
        }
        Reservation approvedReservation = new Reservation(
                reservation.id(),
                reservation.userId(),
                reservation.roomId(),
                reservation.startDate(),
                reservation.endDate(),
                ReservationStatus.APPROVED
        );
        reservationMap.put(reservation.id(), approvedReservation);
        return approvedReservation;
    }

    private boolean isReservationConflict(
            Reservation reservation
    ){
        for(Reservation existingReservation: reservationMap.values()){
            if(reservation.id().equals(existingReservation.id())){
                continue;
            }
            if(!reservation.roomId().equals(existingReservation.roomId())){
                continue;
            }
            if(existingReservation.status().equals(ReservationStatus.APPROVED)){
                continue;
            }
            if(reservation.startDate().isBefore(existingReservation.endDate())
                    && existingReservation.endDate().isBefore(reservation.endDate())){
                return true;
            }
        }
        return false;
    }
}

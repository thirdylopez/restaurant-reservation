package umpisa.restaurant.reservation.services;

import umpisa.restaurant.reservation.model.ReservationDTO;

import java.util.List;

public interface ReservationService {

    List<ReservationDTO> viewReservations();

    ReservationDTO saveNewReservation(ReservationDTO reservationDTO);

    ReservationDTO updateReservationById(Long reservationId, ReservationDTO reservationDTO);

    void cancelReservation(Long reservationId);
}

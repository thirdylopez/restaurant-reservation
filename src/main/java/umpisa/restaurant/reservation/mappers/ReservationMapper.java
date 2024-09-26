package umpisa.restaurant.reservation.mappers;

import umpisa.restaurant.reservation.entities.Reservation;
import umpisa.restaurant.reservation.model.ReservationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReservationMapper {
    ReservationDTO reservationToReservationDTO(Reservation reservationDTO);

    Reservation reservationDTOToReservation(ReservationDTO reservation);
}


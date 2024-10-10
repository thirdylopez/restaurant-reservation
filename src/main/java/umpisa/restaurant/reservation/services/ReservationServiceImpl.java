package umpisa.restaurant.reservation.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import umpisa.restaurant.reservation.controller.NotFoundException;
import umpisa.restaurant.reservation.entities.Reservation;
import umpisa.restaurant.reservation.mappers.ReservationMapper;
import umpisa.restaurant.reservation.model.ReservationDTO;
import umpisa.restaurant.reservation.repositories.ReservationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    private final ReservationMapper reservationMapper;


    @Override
    public List<ReservationDTO> viewReservations() {

        return reservationRepository.findAllByIsActiveAndReservationDateTimeGreaterThan(true, LocalDateTime.now())
                .stream()
                .map(reservationMapper::reservationToReservationDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReservationDTO saveNewReservation(ReservationDTO reservationDTO) {
        return reservationMapper.reservationToReservationDTO(
                reservationRepository.save(
                        createNewReservation(reservationDTO)));
    }

    private Reservation createNewReservation(ReservationDTO reservationDTO) {
        Reservation newReservation = reservationMapper.reservationDTOToReservation(reservationDTO);
        newReservation.setActive(true);
        newReservation.setCreatedDate(LocalDateTime.now());
        newReservation.setUpdateDate(LocalDateTime.now());
        return newReservation;
    }

    @Override
    public ReservationDTO updateReservationById(Long reservationId, ReservationDTO reservationDTO) {
        AtomicReference<ReservationDTO> reservationDTOAtomicReference = new AtomicReference<>();

        reservationRepository.findById(reservationId).ifPresentOrElse(foundReservation -> {
            if (!ObjectUtils.isEmpty(reservationDTO.getNumberOfGuests())) {
                foundReservation.setNumberOfGuests(reservationDTO.getNumberOfGuests());
            }
            if (!ObjectUtils.isEmpty(reservationDTO.getReservationDateTime())) {
                foundReservation.setReservationDateTime(reservationDTO.getReservationDateTime());
            }
            foundReservation.setUpdateDate(LocalDateTime.now());
            reservationDTOAtomicReference.set(reservationMapper.reservationToReservationDTO(reservationRepository.save(foundReservation)));
        }, () -> {
            throw new NotFoundException("Reservation was not found");
        });
        return reservationDTOAtomicReference.get();
    }

    @Override
    public void cancelReservation(Long reservationId) {
        reservationRepository.
                findByIdAndIsActive(reservationId, true).
                ifPresentOrElse(deletedReservation -> {
                    deletedReservation.setActive(false);
                    reservationRepository.save(deletedReservation);
                }, () -> {
                    throw new NotFoundException("Reservation was not found");
                });
    }
}

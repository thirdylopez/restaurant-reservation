package umpisa.restaurant.reservation.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import umpisa.restaurant.reservation.entities.Reservation;
import umpisa.restaurant.reservation.mappers.ReservationMapper;
import umpisa.restaurant.reservation.model.ReservationDTO;
import umpisa.restaurant.reservation.repositories.ReservationRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ReservationControllerIT {
    @Autowired
    ReservationController reservationController;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    ReservationMapper reservationMapper;

    @Test
    void testViewReservations() {
        List<ReservationDTO> reservationList = reservationController.viewReservations();
        assertThat(reservationList.size()).isEqualTo(2);

    }

    @Rollback
    @Transactional
    @Test
    void testViewReservationsNotFound() {
        reservationRepository.deleteAll();
        List<ReservationDTO> reservationList = reservationController.viewReservations();
        assertThat(reservationList.size()).isEqualTo(0);

    }

    @Rollback
    @Transactional
    @Test
    void saveNewReservationTest() {
        ReservationDTO reservationDTO = ReservationDTO.builder().
                customerName("Mel B").email("MelB@email.com").preferredMethod("E")
                .reservationDateTime(LocalDateTime.of(2024, 12, 8, 10, 30)).build();

        ResponseEntity responseEntity = reservationController.createReservation(reservationDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getBody()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        Long savedID = Long.parseLong(locationUUID[4]);

        Reservation reservation = reservationRepository.findById(savedID).get();
        assertThat(reservation.getId()).isNotNull();
    }

    @Rollback
    @Transactional
    @Test
    void updateExistingReservation() {
        Reservation reservation = reservationRepository.findAll().get(0);
        ReservationDTO reservationDTO = reservationMapper.reservationToReservationDTO(reservation);
        reservationDTO.setId(null);
        reservationDTO.setVersion(null);
        final Integer numOfGuests = 6;
        final LocalDateTime newReservationDate = LocalDateTime.of(2024, 10, 1, 10, 0);
        reservationDTO.setNumberOfGuests(numOfGuests);
        reservationDTO.setReservationDateTime(newReservationDate);

        ResponseEntity responseEntity = reservationController.updateReservationById(reservation.getId(), reservationDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));

        Reservation updatedReservation = reservationRepository.findById(reservation.getId()).get();
        assertThat(updatedReservation.getNumberOfGuests()).isEqualTo(numOfGuests);
        assertThat(updatedReservation.getReservationDateTime()).isEqualTo(newReservationDate);
    }

    @Test
    void updateExistingReservationNotFoundTest() {
        assertThrows(NotFoundException.class, () -> {
            reservationController.updateReservationById(100L, ReservationDTO.builder().build());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testCancelReservation() {
        Reservation reservation = reservationRepository.findAll().get(0);

        ResponseEntity responseEntity = reservationController.cancelReservationById(reservation.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));

        assertThat(reservationRepository.findById(reservation.getId()).isEmpty());

    }


    @Test
    void testCancelReservationNotFound() {
        assertThrows(NotFoundException.class, () -> {
            reservationController.cancelReservationById(100L);
        });

    }
}
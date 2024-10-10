package umpisa.restaurant.reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import umpisa.restaurant.reservation.model.ReservationDTO;
import umpisa.restaurant.reservation.services.ReservationService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ReservationController {
    public static final String RESERVATION_PATH = "/api/v1/reservation";
    public static final String RESERVATION_PATH_ID = RESERVATION_PATH + "/{reservationId}";

    private static final String confirmationString = " - Confirmed Reservation for ";

    private final ReservationService reservationService;

    @GetMapping(value = RESERVATION_PATH)
    public List<ReservationDTO> viewReservations() {
        return reservationService.viewReservations();
    }

    @PostMapping(RESERVATION_PATH)
    public ResponseEntity<String> createReservation(@Validated @RequestBody ReservationDTO reservationDTO) {


        ReservationDTO savedReservationDTO = reservationService.saveNewReservation(reservationDTO);

        return ResponseEntity.status(HttpStatus.CREATED).header("Location", RESERVATION_PATH + "/" + savedReservationDTO.getId().toString()).
                body(determineNewReservationResult(savedReservationDTO));
    }

    private String determineNewReservationResult(ReservationDTO savedReservationDTO) {
        StringBuilder resultString = new StringBuilder(confirmationString).append(savedReservationDTO.getCustomerName()).append(" on ");

        resultString.append(savedReservationDTO.getReservationDateTime().getMonth().name() + "/"
                + savedReservationDTO.getReservationDateTime().getDayOfMonth()
                + "/" + savedReservationDTO.getReservationDateTime().getYear()
                + " "
                + savedReservationDTO.getReservationDateTime().getHour() + ":"
                + savedReservationDTO.getReservationDateTime().getMinute());

        if (savedReservationDTO.getPreferredMethod().equals("M")) {
            return "Sent SMS - " + resultString;
        } else {
            return "Sent Email - " + resultString;
        }
    }

    @PutMapping(RESERVATION_PATH_ID)
    public ResponseEntity updateReservationById(@PathVariable("reservationId") Long reservationId,
                                                @Validated @RequestBody ReservationDTO reservationDTO) {

        reservationService.updateReservationById(reservationId, reservationDTO);

        return ResponseEntity.ok("Reservation has been updated.");
    }

    @DeleteMapping(RESERVATION_PATH_ID)
    public ResponseEntity cancelReservationById(@PathVariable("reservationId") Long reservationId) {

        reservationService.cancelReservation(reservationId);

        return ResponseEntity.ok("Reservation has been cancelled.");
    }
}

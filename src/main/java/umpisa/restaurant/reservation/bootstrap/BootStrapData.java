package umpisa.restaurant.reservation.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import umpisa.restaurant.reservation.entities.Reservation;
import umpisa.restaurant.reservation.repositories.ReservationRepository;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class BootStrapData implements CommandLineRunner {

    private final ReservationRepository reservationRepository;

    @Override
    public void run(String... args) throws Exception {

        loadReservationData();
    }


    private void loadReservationData() {
        if (reservationRepository.count() == 0) {
            Reservation reservation1 = Reservation.builder()
                    .customerName("Tony Smith")
                    .email("tony.smith@yahoo.com")
                    .phoneNumber("+639170000000")
                    .preferredMethod("E")
                    .reservationDateTime(LocalDateTime.of(2024, 8, 24, 17, 30))
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .numberOfGuests(2)
                    .isActive(true)
                    .build();

            Reservation reservation2 = Reservation.builder()
                    .customerName("Pocahontas")
                    .email("pocahontas@yahoo.com")
                    .phoneNumber("+639170000000")
                    .preferredMethod("E")
                    .reservationDateTime(LocalDateTime.of(2024, 10,24, 18, 30))
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .isActive(true)
                    .numberOfGuests(2)
                    .build();

            Reservation reservation3 = Reservation.builder()
                    .customerName("Olaf")
                    .email("olaf@yahoo.com")
                    .phoneNumber("+639170000000")
                    .preferredMethod("E")
                    .reservationDateTime(LocalDateTime.of(2024, 10,24, 19, 30))
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .numberOfGuests(2)
                    .isActive(true)
                    .build();

            Reservation reservation4 = Reservation.builder()
                    .customerName("Anna Princess")
                    .phoneNumber("+639170000005")
                    .preferredMethod("M")
                    .reservationDateTime(LocalDateTime.of(2024, 9, 24, 19, 30))
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .numberOfGuests(2)
                    .isActive(false)
                    .build();

            reservationRepository.save(reservation1);
            reservationRepository.save(reservation2);
            reservationRepository.save(reservation3);
            reservationRepository.save(reservation4);

        }

        System.out.println("Reservation Count: " + reservationRepository.count());

    }
}

package umpisa.restaurant.reservation.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Builder
@Data
@ToString
@EqualsAndHashCode(of = {"id"})
public class ReservationDTO {
    private Long id;
    private Integer version;
    private String customerName;
    private String phoneNumber;
    private String email;
    private String preferredMethod;
    private LocalDateTime reservationDateTime;
    private Integer numberOfGuests;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}

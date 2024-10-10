package umpisa.restaurant.reservation.model;

import jakarta.validation.constraints.*;
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
    @NotNull
    @NotBlank
    private String customerName;
    private String phoneNumber;
    @Email
    private String email;
    @NotNull
    @NotBlank
    private String preferredMethod;
    @FutureOrPresent
    private LocalDateTime reservationDateTime;
    @Min(1)
    private Integer numberOfGuests;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}

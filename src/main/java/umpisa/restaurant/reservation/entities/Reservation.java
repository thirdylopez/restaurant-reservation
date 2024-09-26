package umpisa.restaurant.reservation.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = {"id"})
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    @Version
    private Integer version;
    private String customerName;
    @Column(nullable = true)
    private String phoneNumber;
    @Column(nullable = true)
    private String email;
    private LocalDateTime reservationDateTime;
    private String preferredMethod;
    private Integer numberOfGuests;
    @Column(updatable = false)
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
    private boolean isActive;


}

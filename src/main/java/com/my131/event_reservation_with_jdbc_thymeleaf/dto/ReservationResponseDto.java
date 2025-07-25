package com.my131.event_reservation_with_jdbc_thymeleaf.dto;

import com.my131.event_reservation_with_jdbc_thymeleaf.model.Event;
import com.my131.event_reservation_with_jdbc_thymeleaf.model.Reservation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponseDto {
    private Long id;
    private String attendeeName;
    private Integer seat;
    private Event event;

    public static ReservationResponseDto fromEntity(Reservation reservation) {
        return new ReservationResponseDto(
                reservation.getId(),
                reservation.getAttendeeName(),
                reservation.getSeat(),
                reservation.getEvent()
        );
    }
}

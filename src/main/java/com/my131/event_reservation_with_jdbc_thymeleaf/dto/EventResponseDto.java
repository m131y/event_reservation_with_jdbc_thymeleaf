package com.my131.event_reservation_with_jdbc_thymeleaf.dto;

import com.my131.event_reservation_with_jdbc_thymeleaf.model.Event;
import com.my131.event_reservation_with_jdbc_thymeleaf.model.Reservation;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventResponseDto {
    private Long id;
    private String name;
    private String location;
    private LocalDateTime eventDate;

    public static EventResponseDto fromEntity(Event event) {
        return new EventResponseDto(
                event.getId(),
                event.getName(),
                event.getLocation(),
                event.getEventDate()
        );
    }
}

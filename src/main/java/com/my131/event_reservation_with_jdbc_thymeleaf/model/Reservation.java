package com.my131.event_reservation_with_jdbc_thymeleaf.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reservation {
    private Long id;
    private String attendeeName;
    private Integer seat;
    private Event event;
}

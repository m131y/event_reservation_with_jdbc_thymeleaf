package com.my131.event_reservation_with_jdbc_thymeleaf.model;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Event {
    private Long id;
    private String name;
    private String location;
    private LocalDateTime eventDate;
}

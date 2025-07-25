package com.my131.event_reservation_with_jdbc_thymeleaf.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventRequestDto {
    // 요청 단계에서 null 필터링 (논리적 제약)
    @NotBlank(message = "이름 입력하세요")
    private String name;

    private String location;

    // 요청 단계에서 null 필터링 (논리적 제약)
    @NotNull(message = "날짜를 입력해주세요.")
    private LocalDateTime eventDate;
}

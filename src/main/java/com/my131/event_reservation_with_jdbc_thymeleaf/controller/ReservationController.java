package com.my131.event_reservation_with_jdbc_thymeleaf.controller;

import com.my131.event_reservation_with_jdbc_thymeleaf.dto.EventDto;
import com.my131.event_reservation_with_jdbc_thymeleaf.dto.ReservationDto;
import com.my131.event_reservation_with_jdbc_thymeleaf.service.EventService;
import com.my131.event_reservation_with_jdbc_thymeleaf.service.ReservationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    private final EventService eventService;

    /**
     * 전체 예약 목록 조회
     * GET /events/reservations
     */
    @GetMapping("/events/reservations")
    public String list(Model model){
        List<ReservationDto> reservationDtos = reservationService.findAll(); // 모든 예약 조회
        model.addAttribute("reservationDtos", reservationDtos); // 모델에 담기
        return"reservation-list"; // 예약 목록 뷰 렌더링
    }

    /**
     * 예약 등록 처리
     * POST /events/{eventId}/reserve
     * - 이벤트 상세 페이지에서 예약 등록 요청
     * - 유효성 검사 실패 시 다시 이벤트 상세 페이지로 돌아감
     */
    @PostMapping("/events/{eventId}/reserve")
    public String add(
            @PathVariable Long eventId,
            @Valid @ModelAttribute ReservationDto reservationDto, // 입력값 바인딩 + 검증
            BindingResult bindingResult,
            Model model
    ) {
        // 유효성 오류 발생 시: 다시 이벤트 상세 페이지로 돌아감
        if(bindingResult.hasErrors()) {
            EventDto eventDto = eventService.findById(eventId);
            List<ReservationDto> reservationDtos = reservationService.findByEventId(eventId);

            model.addAttribute("eventDto", eventDto);
            model.addAttribute("reservationDto", reservationDto);
            model.addAttribute("reservationDtos", reservationDtos);

            return "event-detail";
        }
        // 유효성 통과 → 예약 저장
        reservationService.save(eventId, reservationDto);
        return "redirect:/events/" + eventId ; // 해당 이벤트 상세 페이지로 리다이렉트
    }

    /**
     * 예약 수정 폼 요청
     * GET /reservations/{id}/edit
     */
    @GetMapping("/reservations/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        ReservationDto reservationDto = reservationService.findById(id); // 예약 조회
        model.addAttribute("reservationDto",reservationDto);

        return "reservation-form"; // 수정 폼 뷰로 이동
    }

    /**
     * 예약 수정 처리
     * POST /reservations/{id}/edit
     */
    @PostMapping("/reservations/{id}/edit")
    public String edit(
            @PathVariable Long id,
            @Valid @ModelAttribute ReservationDto reservationDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) return "reservation-form"; // 오류 시 다시 폼

        reservationService.update(id, reservationDto); // 수정 저장
        Long eventId = reservationService.findById(id).getEventId(); // 해당 이벤트 id 조회
        return "redirect:/events/" + eventId ; // 수정된 이벤트 상세로 이동
    }

    /**
     * 예약 삭제 처리
     * POST /reservations/{id}/cancel
     * - Referer를 활용해 이전 페이지로 돌아가도록 설정
     */
    @PostMapping("/reservations/{id}/cancel")
    public String delete(
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        reservationService.delete(id); // 삭제 수행
        // Referer(이전 페이지 주소)를 읽어 리다이렉션
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/events");
    }
}

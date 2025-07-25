package com.my131.event_reservation_with_jdbc_thymeleaf.controller;

import com.my131.event_reservation_with_jdbc_thymeleaf.dto.EventDto;
import com.my131.event_reservation_with_jdbc_thymeleaf.dto.ReservationDto;
import com.my131.event_reservation_with_jdbc_thymeleaf.service.EventService;
import com.my131.event_reservation_with_jdbc_thymeleaf.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final ReservationService reservationService;

    /**
     * 이벤트 목록 조회
     * GET /events
     */
    @GetMapping
    public String list(Model model){
        List<EventDto> list = eventService.findAll(); // 모든 이벤트 조회
        model.addAttribute("eventDtos", list);
        return"event-list"; // 이벤트 목록 뷰로 이동
    }

    /**
     * 이벤트 상세 보기 + 해당 이벤트의 예약 목록
     * GET /events/{id}
     */
    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model){
        EventDto eventDto = eventService.findById(id); // 이벤트 단일 조회
        List<ReservationDto> reservationDtos = reservationService.findByEventId(id); // 이벤트에 연결된 예약 목록
        model.addAttribute("eventDto", eventDto);
        model.addAttribute("reservationDtos", reservationDtos);
        model.addAttribute("reservationDto", new ReservationDto()); // 예약 등록용 빈 폼 객체
        return "event-detail"; // 이벤트 상세 페이지로 이동
    }

    /**
     * 이벤트 등록 폼 요청
     * GET /events/add
     */
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("eventDto",new EventDto()); // 빈 이벤트 객체 전달

        return "event-form"; // 등록 폼 뷰로 이동
    }

    /**
     * 이벤트 등록 처리
     * POST /events/add
     */
    @PostMapping("/add")
    public String add(
            @Valid @ModelAttribute EventDto eventDto,
            BindingResult bindingResult
    ) {
        if(bindingResult.hasErrors()) return "event-form"; // 오류 있을 경우 다시 폼

        eventService.save(eventDto); // 이벤트 저장
        return "redirect:/events"; // 목록으로 리다이렉트
    }

    /**
     * 이벤트 수정 폼 요청
     * GET /events/{id}/edit
     */
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        EventDto eventDto = eventService.findById(id); // 수정할 이벤트 정보
        model.addAttribute("eventDto",eventDto);

        return "event-form"; // 등록과 동일한 폼 재사용
    }

    /**
     * 이벤트 수정 처리
     * POST /events/{id}/edit
     */
    @PostMapping("/{id}/edit")
    public String edit(
            @PathVariable Long id,
            @Valid @ModelAttribute EventDto eventDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) return "event-form"; // 오류 있을 경우 수정 폼 다시 보여줌

        eventService.update(id, eventDto); // 업데이트 수행
        return "redirect:/events"; // 목록으로 리다이렉트
    }

    /**
     * 이벤트 삭제 처리
     * POST /events/{id}/delete
     */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        eventService.delete(id); // 이벤트 삭제
        return "redirect:/events"; // 목록으로 리다이렉트
    }
}

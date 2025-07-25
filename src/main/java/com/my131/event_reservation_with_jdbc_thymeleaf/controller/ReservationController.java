package com.my131.event_reservation_with_jdbc_thymeleaf.controller;

import com.my131.event_reservation_with_jdbc_thymeleaf.dto.EventDto;
import com.my131.event_reservation_with_jdbc_thymeleaf.dto.EventRequestDto;
import com.my131.event_reservation_with_jdbc_thymeleaf.dto.ReservationDto;
import com.my131.event_reservation_with_jdbc_thymeleaf.dto.ReservationRequestDto;
import com.my131.event_reservation_with_jdbc_thymeleaf.model.Reservation;
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

    @GetMapping("/events/reservations")
    public String list(Model model){
        List<ReservationDto> reservationDtos = reservationService.findAll();
        model.addAttribute("reservationDtos", reservationDtos);
        return"reservation-list";
    }

    @PostMapping("/events/{eventId}/reserve")
    public String add(
            @PathVariable Long eventId,
            @Valid @ModelAttribute ReservationDto reservationDto,
            BindingResult bindingResult
    ) {
        if(bindingResult.hasErrors()) return "reservation-form";

        reservationService.save(eventId, reservationDto);
        return "redirect:/events/" + eventId ;
    }

    @GetMapping("/reservations/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        ReservationDto reservationDto = reservationService.findById(id);
        model.addAttribute("reservationDto",reservationDto);

        return "reservation-form";
    }

    @PostMapping("/reservations/{id}/edit")
    public String edit(
            @PathVariable Long id,
            @Valid @ModelAttribute ReservationDto reservationDto
    ) {
        reservationService.update(id, reservationDto);
        Long eventId = reservationService.findById(id).getEventId();
        return "redirect:/events/" + eventId ;
    }

    @PostMapping("/reservations/{id}/cancel")
    public String delete(
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        Long eventId = reservationService.findById(id).getEventId();
        reservationService.delete(id);
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/events");
    }
}

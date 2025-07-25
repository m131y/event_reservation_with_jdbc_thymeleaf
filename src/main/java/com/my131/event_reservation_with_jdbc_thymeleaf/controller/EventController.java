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

    @GetMapping
    public String list(Model model){
        List<EventDto> list = eventService.findAll();
        model.addAttribute("eventDtos", list);
        return"event-list";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model){
        EventDto eventDto = eventService.findById(id);
        List<ReservationDto> reservationDtos = reservationService.findByEventId(id);
        model.addAttribute("eventDto", eventDto);
        model.addAttribute("reservationDtos", reservationDtos);
        model.addAttribute("reservationDto", new ReservationDto());
        return "event-detail";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("eventDto",new EventDto());

        return "event-form";
    }

    @PostMapping("/add")
    public String add(
            @Valid @ModelAttribute EventDto eventDto,
            BindingResult bindingResult
    ) {
        if(bindingResult.hasErrors()) return "event-form";

        eventService.save(eventDto);
        return "redirect:/events";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        EventDto eventDto = eventService.findById(id);
        model.addAttribute("eventDto",eventDto);

        return "event-form";
    }

    @PostMapping("/{id}/edit")
    public String edit(
            @PathVariable Long id,
            @Valid @ModelAttribute EventDto eventDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) return "event-form";

        eventService.update(id, eventDto);
        return "redirect:/events";
    }

    @PostMapping("/{id}/delete")
    public String delete(
            @PathVariable Long id
    ) {
        eventService.delete(id);
        return "redirect:/events";
    }
}

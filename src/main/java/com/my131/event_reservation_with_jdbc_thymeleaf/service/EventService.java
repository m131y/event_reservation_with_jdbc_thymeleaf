package com.my131.event_reservation_with_jdbc_thymeleaf.service;


import com.my131.event_reservation_with_jdbc_thymeleaf.dto.EventDto;
import com.my131.event_reservation_with_jdbc_thymeleaf.dto.EventRequestDto;
import com.my131.event_reservation_with_jdbc_thymeleaf.dto.EventResponseDto;
import com.my131.event_reservation_with_jdbc_thymeleaf.model.Event;
import com.my131.event_reservation_with_jdbc_thymeleaf.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional
public class EventService {
    private final EventRepository eventRepository;

    public List<EventDto> findAll(){
        List<Event> events = eventRepository.findAll();
        List<EventDto> eventDtos = new ArrayList<>();

        eventDtos = events.stream().map(event -> {
            return new EventDto(
                    event.getId(),
                    event.getName(),
                    event.getLocation(),
                    event.getEventDate()
            );
        }).toList();
        System.out.println("events:"+events);
        System.out.println("eventDtos"+eventDtos);
        return eventDtos;
    }

    public EventDto findById(Long id){
        Event event = eventRepository.findById(id);
        EventDto eventDto = new EventDto();

        eventDto.setId(event.getId());
        eventDto.setName(event.getName());
        eventDto.setLocation(event.getLocation());
        eventDto.setEventDate(event.getEventDate());

        return eventDto;
    }

    public void save(EventDto eventDto){
        eventRepository.save(eventDto);
    }

    public void update(Long id, EventDto eventDto){
        eventRepository.update(id, eventDto);
    }

    public void delete(Long id){
        eventRepository.delete(id);
    }
}

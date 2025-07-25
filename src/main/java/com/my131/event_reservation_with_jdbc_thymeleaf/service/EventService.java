package com.my131.event_reservation_with_jdbc_thymeleaf.service;


import com.my131.event_reservation_with_jdbc_thymeleaf.dto.EventDto;
import com.my131.event_reservation_with_jdbc_thymeleaf.model.Event;
import com.my131.event_reservation_with_jdbc_thymeleaf.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EventService {
    private final EventRepository eventRepository;
    // 이벤트 전체 목록 조회
    public List<EventDto> findAll(){
        // Event, List<Event> 객체를 EventDto, List<EventDto>로 변환
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
        return eventDtos;
    }

    // 이벤트 단일 조회
    public EventDto findById(Long id){
        // Event 객체를 EventDto로 변환
        Event event = eventRepository.findById(id);
        EventDto eventDto = new EventDto();

        eventDto.setId(event.getId());
        eventDto.setName(event.getName());
        eventDto.setLocation(event.getLocation());
        eventDto.setEventDate(event.getEventDate());

        return eventDto;
    }

    // 이벤트 등록
    public void save(EventDto eventDto){
        eventRepository.save(eventDto);
    }
    // 이벤트 수정
    public void update(Long id, EventDto eventDto){
        eventRepository.update(id, eventDto);
    }
    // 이벤트 삭제
    public void delete(Long id){
        eventRepository.delete(id);
    }
}

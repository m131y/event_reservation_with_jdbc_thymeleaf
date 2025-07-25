package com.my131.event_reservation_with_jdbc_thymeleaf.service;

import com.my131.event_reservation_with_jdbc_thymeleaf.dto.EventDto;
import com.my131.event_reservation_with_jdbc_thymeleaf.dto.EventRequestDto;
import com.my131.event_reservation_with_jdbc_thymeleaf.dto.ReservationDto;
import com.my131.event_reservation_with_jdbc_thymeleaf.dto.ReservationRequestDto;
import com.my131.event_reservation_with_jdbc_thymeleaf.model.Event;
import com.my131.event_reservation_with_jdbc_thymeleaf.model.Reservation;
import com.my131.event_reservation_with_jdbc_thymeleaf.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public List<ReservationDto> findAll(){
        List<Reservation> reservations = reservationRepository.findAll();
        List<ReservationDto> reservationDtos = new ArrayList<>();

        reservationDtos = reservations.stream().map(reservation -> {
            return new ReservationDto(
                    reservation.getId(),
                    reservation.getAttendeeName(),
                    reservation.getSeat(),
                    reservation.getEvent().getId()
            );
        }).toList();
        System.out.println("reservations:"+reservations);
        System.out.println("reservationDtos"+reservationDtos);
        return reservationDtos;
    }

    public ReservationDto findById(Long id){
        Reservation reservation = reservationRepository.findById(id);
        ReservationDto reservationDto = new ReservationDto();

        reservationDto.setId(reservation.getId());
        reservationDto.setAttendeeName(reservation.getAttendeeName());
        reservationDto.setSeats(reservation.getSeat());
        reservationDto.setEventId(reservation.getEvent().getId());

        return reservationDto;
    }

    public List<ReservationDto> findByEventId(Long eventId){
        List<Reservation> reservations = reservationRepository.findByEventId(eventId);
        List<ReservationDto> reservationDtos = new ArrayList<>();

        reservationDtos = reservations.stream().map(reservation -> {
            return new ReservationDto(
                    reservation.getId(),
                    reservation.getAttendeeName(),
                    reservation.getSeat(),
                    reservation.getEvent().getId()
            );
        }).toList();
        return reservationDtos;
    }

    public void save(Long eventId, ReservationDto reservationDto){
        reservationRepository.save(eventId, reservationDto);
    }

    public void update(Long id, ReservationDto reservationDto){
        reservationRepository.update(id, reservationDto);
    }

    public void delete(Long id){
        reservationRepository.delete(id);
    }


}

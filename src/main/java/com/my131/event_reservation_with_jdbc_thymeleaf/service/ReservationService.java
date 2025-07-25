package com.my131.event_reservation_with_jdbc_thymeleaf.service;

import com.my131.event_reservation_with_jdbc_thymeleaf.dto.ReservationDto;
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
    // 예약 전체 목록 조회
    public List<ReservationDto> findAll(){
        // Reservation, List<Reservation> 객체를 ReservationDto, List<ReservationDto>로 변환
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
        return reservationDtos;
    }

    // 예약 단일 조회
    public ReservationDto findById(Long id){
        // Reservation 객체를 ReservationDto로 변환
        Reservation reservation = reservationRepository.findById(id);
        ReservationDto reservationDto = new ReservationDto();

        reservationDto.setId(reservation.getId());
        reservationDto.setAttendeeName(reservation.getAttendeeName());
        reservationDto.setSeats(reservation.getSeat());
        reservationDto.setEventId(reservation.getEvent().getId());

        return reservationDto;
    }

    // 예약 이벤트 별 조회
    public List<ReservationDto> findByEventId(Long eventId){
        // Reservation, List<Reservation> 객체를 ReservationDto, List<ReservationDto>로 변환
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
    // 예약 등록
    public void save(Long eventId, ReservationDto reservationDto){
        reservationRepository.save(eventId, reservationDto);
    }
    // 예약 수정
    public void update(Long id, ReservationDto reservationDto){
        reservationRepository.update(id, reservationDto);
    }
    // 예약 삭제
    public void delete(Long id){
        reservationRepository.delete(id);
    }


}

package com.my131.event_reservation_with_jdbc_thymeleaf.repository;

import com.my131.event_reservation_with_jdbc_thymeleaf.dto.ReservationDto;
import com.my131.event_reservation_with_jdbc_thymeleaf.model.Event;
import com.my131.event_reservation_with_jdbc_thymeleaf.model.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReservationRepository {
    private final EventRepository eventRepository;
    private final JdbcTemplate jdbcTemplate;

    private RowMapper<Reservation> reservationRowMapper() {
        return (resultSet, rowNum) -> {
            Long eventId = resultSet.getLong("event_id");
            Event event = eventRepository.findById(eventId);

            return Reservation.builder()
                    .id(resultSet.getLong("id"))
                    .attendeeName(resultSet.getString("attendee_name"))
                    .seat(resultSet.getInt("seats"))
                    .event(event)
                    .build();
        };
    }

    // 예약 전체 조회, id 순으로 정렬
    public List<Reservation> findAll() {
        String sql = "SELECT * FROM reservations ORDER BY id ASC;";

        return jdbcTemplate.query(sql, reservationRowMapper());
    }
    // 예약 단일 조회
    public Reservation findById(Long id){
        String sql = "SELECT * FROM reservations WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, reservationRowMapper(), id);
    }
    // 예약 이벤트 별 조회, id 순으로 정렬
    public List<Reservation> findByEventId(Long eventId){
        String sql = "SELECT * FROM reservations WHERE event_id = ? ORDER BY id ASC";

        return jdbcTemplate.query(sql, reservationRowMapper(), eventId);
    }
    // 예약 등록
    public int save(Long eventId, ReservationDto reservationDto){
        Event event = eventRepository.findById(eventId);
        String sql = "INSERT INTO reservations (attendee_name, seats, event_id) VALUES (?,?,?)";

        return jdbcTemplate.update(sql,reservationDto.getAttendeeName(),reservationDto.getSeats(),event.getId());
    }
    // 예약 수정
    public int update(Long id, ReservationDto reservationDto){
        String sql =  "UPDATE reservations SET attendee_name =?, seats = ? WHERE id = ?";

        return jdbcTemplate.update(sql, reservationDto.getAttendeeName(), reservationDto.getSeats(), id);
    }
    // 예약 삭제
    public int delete(Long id){
        String sql =  "DELETE FROM reservations WHERE id = ?";

        return jdbcTemplate.update(sql, id);
    }
}

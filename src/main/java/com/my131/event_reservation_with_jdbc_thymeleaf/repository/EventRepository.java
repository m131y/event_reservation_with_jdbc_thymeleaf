package com.my131.event_reservation_with_jdbc_thymeleaf.repository;

import com.my131.event_reservation_with_jdbc_thymeleaf.dto.EventDto;
import com.my131.event_reservation_with_jdbc_thymeleaf.model.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EventRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Event> EventRowMapper = (resultSet, rowNum) -> Event.builder()
            .id(resultSet.getLong("id"))
            .name(resultSet.getString("name"))
            .location(resultSet.getString("location"))
            .eventDate(resultSet.getTimestamp("event_date").toLocalDateTime())
            .build();

    public List<Event> findAll(){
        String sql = "SELECT * FROM events ORDER BY id ASC;";

        return jdbcTemplate.query(sql, EventRowMapper);
    }

    public Event findById(Long id){
        String sql = "SELECT * FROM events WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, EventRowMapper, id);
    }

    public int save(EventDto eventDto){
        String sql = "INSERT INTO events (name, location, event_date) VALUES (?,?,?)";

        return jdbcTemplate.update(sql, eventDto.getName(), eventDto.getLocation(), Timestamp.valueOf(eventDto.getEventDate()));
    }

    public int update(Long id, EventDto eventDto){
        String sql =  "UPDATE events SET name = ?, location = ?, event_date =? WHERE id = ?";

        return jdbcTemplate.update(sql, eventDto.getName(), eventDto.getLocation(), Timestamp.valueOf(eventDto.getEventDate()), id);
    }

    public int delete(Long id){
        String sql =  "DELETE FROM events WHERE id = ?";

        return jdbcTemplate.update(sql, id);

    }

}

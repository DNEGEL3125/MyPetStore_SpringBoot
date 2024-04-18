package cn.csu.mypetstore_springboot.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "logging_event")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    @Column(name = "timestmp", nullable = false)
    private Long timestamp;

    @Column(name = "level_string", nullable = false)
    private String level;

    @Column(name = "logger_name")
    private String loggerName;

    @Column(name = "formatted_message", nullable = false, length = 2000)
    private String message;

    public Log() {
        this.timestamp = Instant.now().toEpochMilli();
    }
}

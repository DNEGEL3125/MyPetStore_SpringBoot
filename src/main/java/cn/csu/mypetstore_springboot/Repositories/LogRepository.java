package cn.csu.mypetstore_springboot.Repositories;

import cn.csu.mypetstore_springboot.domain.Log;
import org.slf4j.event.LoggingEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long> {
    @Transactional
    @Query(value = "SELECT * FROM logging_event ORDER BY timestmp DESC LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Log> getByLimitAndOffset(int limit, int offset);
}

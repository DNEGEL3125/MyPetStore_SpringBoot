package cn.csu.mypetstore_springboot.Repositories;

import cn.csu.mypetstore_springboot.domain.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long> {
    @Transactional
    @Query(value = "SELECT * FROM logging_event WHERE event_id < :id ORDER BY timestmp DESC LIMIT :limit ", nativeQuery = true)
    List<Log> getByIdBeforeAndLimit(Long id, int limit);

    @Transactional
    @Query(value = "SELECT * FROM logging_event WHERE event_id > :id ORDER BY timestmp DESC", nativeQuery = true)
    List<Log> getByIdAfter(Long id);
}

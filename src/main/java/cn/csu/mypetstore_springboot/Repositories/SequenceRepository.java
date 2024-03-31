package cn.csu.mypetstore_springboot.Repositories;

import cn.csu.mypetstore_springboot.domain.Sequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface SequenceRepository extends JpaRepository<Sequence, Long> {
    Sequence getSequenceByName(String name);

    @Transactional
    @Modifying
    @Query(value = "UPDATE SEQUENCE SET NEXTID = :nextid WHERE NAME = :name", nativeQuery = true)
    void updateNextidByName(@Param("nextid") int nextid, @Param("name") String name);
}

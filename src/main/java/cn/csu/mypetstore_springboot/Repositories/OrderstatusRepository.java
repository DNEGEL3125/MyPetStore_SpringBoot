package cn.csu.mypetstore_springboot.Repositories;

import cn.csu.mypetstore_springboot.domain.Orderstatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderstatusRepository extends JpaRepository<Orderstatus, Long> {
}

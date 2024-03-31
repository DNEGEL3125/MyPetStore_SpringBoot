package cn.csu.mypetstore_springboot.Repositories;

import cn.csu.mypetstore_springboot.domain.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE INVENTORY SET quantity = :quantity WHERE ITEMID = :itemid", nativeQuery = true)
    void updateQuantityByItemid(@Param("itemid") String itemid, @Param("quantity") int quantity);

    Inventory getInventoryByItemid(String itemid);
}

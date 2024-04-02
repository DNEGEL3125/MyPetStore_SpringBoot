package cn.csu.mypetstore_springboot.Repositories;

import cn.csu.mypetstore_springboot.domain.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Available
 */
@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE INVENTORY SET quantity = :quantity WHERE item_id = :itemId", nativeQuery = true)
    void updateQuantityByItemId(String itemId, @Param("quantity") int quantity);

    Inventory getInventoryByItemId(String itemId);
}

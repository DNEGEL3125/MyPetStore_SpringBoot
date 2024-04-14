package cn.csu.mypetstore_springboot.Repositories;

import cn.csu.mypetstore_springboot.domain.LineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LineItemRepository extends JpaRepository<LineItem, Long> {
    @Transactional
    @Query(value = "SELECT * FROM line_item WHERE order_id = :orderId LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<LineItem> getLineItemsByOrderId(Long orderId, int limit, int offset);

    Long countByOrderId(Long orderId);

    @Transactional
    @Query(value = "SELECT * FROM line_item INNER JOIN product p on line_item.product_id = p.product_id INNER JOIN pet_breed pb on p.pet_breed_id = pb.id", nativeQuery = true)
    List<LineItem> getLineItemsByOrder(Long orderId, int limit, int offset);
}

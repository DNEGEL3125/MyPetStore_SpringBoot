package cn.csu.mypetstore_springboot.Repositories;

import cn.csu.mypetstore_springboot.domain.LineItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LineItemRepository extends JpaRepository<LineItem, Long> {
    List<LineItem> getLineItemsByOrderId(int orderId);


}

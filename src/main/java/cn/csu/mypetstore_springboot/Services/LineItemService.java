package cn.csu.mypetstore_springboot.Services;

import cn.csu.mypetstore_springboot.Repositories.LineItemRepository;
import cn.csu.mypetstore_springboot.domain.LineItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LineItemService {
    private final LineItemRepository lineItemRepository;

    public LineItemService(LineItemRepository lineItemRepository) {
        this.lineItemRepository = lineItemRepository;
    }

    public List<LineItem> getLineItemsByOrderId(Long orderId, int pageNumber, int limit) {
        int offset = (pageNumber - 1) * limit;
        return lineItemRepository.getLineItemsByOrderId(orderId, limit, offset);
    }

    public Long getTotalPageNumberByOrderId(Long orderId, int limit) {
        long count = lineItemRepository.countByOrderId(orderId);
        return (count - 1) / limit + 1;
    }
}

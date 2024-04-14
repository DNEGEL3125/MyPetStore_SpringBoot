package cn.csu.mypetstore_springboot.Services;

import cn.csu.mypetstore_springboot.Repositories.LineItemRepository;
import cn.csu.mypetstore_springboot.Repositories.LineItemRepositoryC;
import cn.csu.mypetstore_springboot.domain.LineItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class LineItemService {
    private final LineItemRepository lineItemRepository;
    private final LineItemRepositoryC lineItemRepositoryC;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public LineItemService(LineItemRepository lineItemRepository, LineItemRepositoryC lineItemRepositoryC) {
        this.lineItemRepository = lineItemRepository;
        this.lineItemRepositoryC = lineItemRepositoryC;
    }

    public List<LineItem> getLineItemsByOrderId(Long orderId, int pageNumber, int limit) {
        int offset = (pageNumber - 1) * limit;
        return lineItemRepository.getLineItemsByOrderId(orderId, limit, offset);
    }

    public Long getTotalPageNumberByOrderId(Long orderId, int limit) {
        long count = lineItemRepository.countByOrderId(orderId);
        return (count - 1) / limit + 1;
    }

    public List<LineItem> searchLineItems(Long orderId, String searchKeyword, String searchFor, int pageNumber, int limit) {
        int offset = (pageNumber - 1) * limit;
        try {
            return lineItemRepositoryC.searchLineItemsByContains(orderId, searchFor, searchKeyword, limit, offset);
        } catch (NoSuchFieldException e) {
            logger.error(e.getMessage());
        }

        return new ArrayList<>();
    }

    public Long getTotalPageNumber(Long orderId, String searchKeyword, String searchFor, int limit) {
        Long countLineItemsByContains;
        try {
            countLineItemsByContains = lineItemRepositoryC.countLineItemsByContains(orderId, searchFor, searchKeyword);
        } catch (NoSuchFieldException e) {
            logger.error("Fail to get total page number: " + e.getMessage());
            return 1L;
        }
        return (countLineItemsByContains - 1) / limit + 1;
    }
}

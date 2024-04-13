package cn.csu.mypetstore_springboot.Services;

import cn.csu.mypetstore_springboot.DTO.CategorySalesDTO;
import cn.csu.mypetstore_springboot.DTO.OrderCountDTO;
import cn.csu.mypetstore_springboot.DTO.TotalRevenueDTO;
import cn.csu.mypetstore_springboot.Repositories.OrderRepository;
import cn.csu.mypetstore_springboot.Repositories.OrderRepositoryC;
import cn.csu.mypetstore_springboot.domain.Order;
import cn.csu.mypetstore_springboot.utils.CamelToSnakeConverter;
import cn.csu.mypetstore_springboot.utils.ObjectFieldCopier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class OrderService {
    private final static String AVAILABLE_SEARCH_FOR_REGEX = "[a-zA-Z0-9_.]+";


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OrderRepository orderRepository;
    final OrderRepositoryC orderRepositoryC;

    public OrderService(OrderRepository orderRepository, OrderRepositoryC orderRepositoryC) {
        this.orderRepository = orderRepository;
        this.orderRepositoryC = orderRepositoryC;
    }


    public List<Order> getOrders(Integer page, int limit) {
        if (page == null) {
            page = 1;
        }
        int offset = (page - 1) * limit;

        return orderRepository.getOrdersByLimitAndOffset(limit, offset);
    }


    public List<Order> searchOrders(Integer page, int limit, String searchFor, String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return getOrders(page, limit);
        }

        if (page == null) {
            page = 1;
        }
        int offset = (page - 1) * limit;

        Field declaredField;

        try {
            declaredField = Order.class.getDeclaredField(searchFor);

        } catch (NoSuchFieldException e) {
            logger.info("searchFor='%s' in searchOrders doesn't exist"
                    .formatted(searchFor));
            return new ArrayList<>();
        }

        String colName = CamelToSnakeConverter.camelToSnake(searchFor);

        if (declaredField.getType() == String.class) {
            return orderRepositoryC.searchOrdersByContains(colName, keyword, limit, offset);
        } else {
            return orderRepositoryC.getOrderBy(colName, Long.valueOf(keyword), limit, offset);
        }
    }

    /**
     * for i in range(fields.len){
     * if(a.fields[i] == null){
     * b.fields[i] = a.fields[i];
     * }
     * }
     *
     * @param changedAttrMap orderId to attrName to changedVal
     * @return Update result
     */
    public ResponseEntity<String> updateOrdersByIds(Map<String, Order> changedAttrMap) {
        try {
            for (var orderIdKeyEntry : changedAttrMap.entrySet()) {
                String entryKey = orderIdKeyEntry.getKey();
                Order changedAttrs = orderIdKeyEntry.getValue();

                // New order
                if (entryKey.contains("new")) {
                    changedAttrs.setOrderDate(LocalDateTime.now());
                    orderRepository.save(changedAttrs);
                    continue;
                }

                long orderId = Long.parseLong(entryKey);

                Order order = orderRepository.getOrderByOrderId(orderId);

                ObjectFieldCopier.copyFieldsIfNotNullRecursively(changedAttrs, order);

                // Update the account
                orderRepository.save(order);
            }
        } catch (Exception e) {
            logger.error(e.toString());
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok("Orders updated successfully");
    }

    public Long getMaxPageNumber(int limit) {
        return (orderRepository.count() - 1) / limit + 1;
    }

    public Long getMaxPageNumber(int limit, String keyword, String searchFor) {
        if (keyword == null || keyword.isEmpty()) {
            return getMaxPageNumber(limit);
        }

        Field declaredField;

        try {
            declaredField = Order.class.getDeclaredField(searchFor);

        } catch (NoSuchFieldException e) {
            logger.info("searchFor='%s' in searchOrders doesn't exist"
                    .formatted(searchFor));
            return 1L;
        }


        String colName = CamelToSnakeConverter.camelToSnake(searchFor);


        Long recordsCount;

        if (declaredField.getType() == String.class) {
            recordsCount = orderRepositoryC.countOrdersByContains(colName, keyword);
        } else {
            recordsCount = orderRepositoryC.countOrderBy(colName, Long.valueOf(keyword));
        }


        return (recordsCount - 1) / limit + 1;
    }

    public List<OrderCountDTO> getOrderCountData(String timeScale) {
        List<OrderCountDTO> orderCountData = new ArrayList<>();
        LocalDate dateNow = LocalDate.now();
        int currentYear = dateNow.getYear();

        switch (timeScale.toLowerCase()) {
            case "year" -> orderCountData = orderRepository.countOrdersByYear();
            case "month" -> orderCountData = orderRepository.countOrdersByMonth(currentYear);
            case "day" -> {
                List<Object[]> dayData = orderRepository.countOrdersByDay(
                        currentYear,
                        dateNow.getMonthValue()
                );
                for (Object[] obj : dayData) {
                    int day = (int) obj[2];
                    long count = (long) obj[3];
                    orderCountData.add(new OrderCountDTO(day, count));
                }
            }
            default -> logger.error("Invalid time scale: " + timeScale);
        }

        return orderCountData;
    }

    public List<TotalRevenueDTO> getTotalRevenueData(String timeScale) {
        List<TotalRevenueDTO> totalRevenueData = new ArrayList<>();
        LocalDate dateNow = LocalDate.now();
        int currentYear = dateNow.getYear();

        switch (timeScale.toLowerCase()) {
            case "year" -> totalRevenueData = orderRepository.sumTotalPriceByYear();
            case "month" -> totalRevenueData = orderRepository.sumTotalPriceByMonth(currentYear);
            default -> logger.error("Invalid time scale: " + timeScale);
        }

        return totalRevenueData;
    }

    public List<CategorySalesDTO> getCategorySales(String timeScale) {
        List<CategorySalesDTO> categorySalesData = new ArrayList<>();
        LocalDate dateNow = LocalDate.now();
        int currentYear = dateNow.getYear();

        //            case "year" -> categorySalesData = orderRepository.sumTotalPriceByYear();
        //            case "month" -> categorySalesData = orderRepository.sumTotalPriceByMonth(currentYear);
        logger.error("Invalid time scale: " + timeScale);

        return categorySalesData;
    }
}

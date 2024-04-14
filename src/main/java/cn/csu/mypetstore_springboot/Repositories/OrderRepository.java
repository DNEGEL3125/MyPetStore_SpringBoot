package cn.csu.mypetstore_springboot.Repositories;

import cn.csu.mypetstore_springboot.DTO.CategorySalesDTO;
import cn.csu.mypetstore_springboot.DTO.OrderCountDTO;
import cn.csu.mypetstore_springboot.DTO.TotalRevenueDTO;
import cn.csu.mypetstore_springboot.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> getOrdersByUserId(Long userId);

    Order getOrderByOrderId(Long orderId);

    @Transactional
    @Query(value = "SELECT * FROM orders ORDER BY order_date DESC LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Order> getOrdersByLimitAndOffset(int limit, int offset);

    @Query("SELECT YEAR(o.orderDate), COUNT(o) FROM Order o WHERE YEAR(o.orderDate) BETWEEN :startYear AND :endYear GROUP BY YEAR(o.orderDate)")
    List<Object[]> countOrdersByYear(int startYear, int endYear);


    @Query("""
            SELECT NEW cn.csu.mypetstore_springboot.DTO.OrderCountDTO(YEAR(o.orderDate), COUNT(o))
             FROM Order o GROUP BY YEAR(o.orderDate) ORDER BY YEAR(o.orderDate)""")
    List<OrderCountDTO> countOrdersByYear();

    @Query("""
            SELECT NEW cn.csu.mypetstore_springboot.DTO.OrderCountDTO(MONTH(o.orderDate), COUNT(o))
             FROM Order o WHERE YEAR(o.orderDate) = :year
             GROUP BY YEAR(o.orderDate), MONTH(o.orderDate) ORDER BY MONTH (o.orderDate)""")
    List<OrderCountDTO> countOrdersByMonth(int year);

    @Query("""
            SELECT YEAR(o.orderDate), MONTH(o.orderDate), DAY(o.orderDate), COUNT(o)
             FROM Order o
             WHERE YEAR(o.orderDate) = :year
             AND MONTH(o.orderDate) = :month
             GROUP BY YEAR(o.orderDate), MONTH(o.orderDate), DAY(o.orderDate)""")
    List<Object[]> countOrdersByDay(int year, int month);

    @Query("""
            SELECT NEW cn.csu.mypetstore_springboot.DTO.TotalRevenueDTO(YEAR(o.orderDate), SUM(o.totalPrice))
             FROM Order o GROUP BY YEAR(o.orderDate) ORDER BY YEAR(o.orderDate)""")
    List<TotalRevenueDTO> sumTotalPriceByYear();

    @Query("""
            SELECT NEW cn.csu.mypetstore_springboot.DTO.TotalRevenueDTO(YEAR(o.orderDate), SUM(o.totalPrice))
             FROM Order o WHERE YEAR(o.orderDate) = :year ORDER BY MONTH (o.orderDate)""")
    List<TotalRevenueDTO> sumTotalPriceByMonth(int year);

    @Query("""
            SELECT NEW cn.csu.mypetstore_springboot.DTO.TotalRevenueDTO(YEAR(o.orderDate), SUM(o.totalPrice))
             FROM Order o GROUP BY YEAR(o.orderDate) ORDER BY YEAR(o.orderDate)""")
    List<CategorySalesDTO> sumTotalPriceByYearAndCategoryId();
}

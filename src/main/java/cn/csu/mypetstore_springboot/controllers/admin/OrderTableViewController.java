package cn.csu.mypetstore_springboot.controllers.admin;

import cn.csu.mypetstore_springboot.Services.OrderService;
import cn.csu.mypetstore_springboot.domain.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// TODO 订单管理模块
@Controller
@RequestMapping("/admin/orders")
public class OrderTableViewController {
    private static final String ORDER_LIST_PAGE = "admin/OrdersView";
    final static int PRODUCTS_PER_PAGE = 16;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final OrderService orderService;

    public OrderTableViewController(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping("/view")
    public String orderTableView(Model model) {
        model.addAttribute("pageTitle", "Orders view");
        model.addAttribute("ordersList", new ArrayList<Order>());
        return ORDER_LIST_PAGE;
    }

    @RequestMapping("/update")
    public ResponseEntity<String> updateOrdersByIds(@RequestBody Map<String, Order> changedAttrMap) {
        logger.info("Modified id: " + changedAttrMap.toString());
        return orderService.updateOrdersByIds(changedAttrMap);
    }

    @RequestMapping("/view/table/row/empty")
    public String getOrderTableEmptyRow(
            Model model
    ) {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order());
        model.addAttribute("ordersList", orders);

        return "/admin/OrderTableRow";
    }

    @RequestMapping("/view/table")
    public String getOrderTable(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            Model model
    ) {
        List<Order> orders = orderService.getOrders(pageNumber, PRODUCTS_PER_PAGE);
        model.addAttribute("ordersList", orders);

        return "/admin/OrderTable";
    }

    @RequestMapping("/search/view/table")
    public String getSearchOrderTable(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(value = "searchFor") String searchFor,
            Model model
    ) {
        List<Order> orders = orderService.searchOrders(page, PRODUCTS_PER_PAGE, searchFor, keyword);
        model.addAttribute("ordersList", orders);
        return "/admin/OrderTable";
    }

    @RequestMapping("/view/maxPageNumber")
    public ResponseEntity<Long> getMaxPageNumber() {
        Long maxPages = orderService.getMaxPageNumber(PRODUCTS_PER_PAGE);
        return ResponseEntity.ok(maxPages);
    }


    @RequestMapping("/search/view/maxPageNumber")
    public ResponseEntity<Long> getMaxPageNumber(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                                                 @RequestParam(value = "searchFor") String searchFor) {
        Long maxPageNumber = orderService.getMaxPageNumber(PRODUCTS_PER_PAGE, keyword, searchFor);
        return ResponseEntity.ok(maxPageNumber);
    }
}

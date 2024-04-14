package cn.csu.mypetstore_springboot.controllers.admin;

import cn.csu.mypetstore_springboot.Services.LineItemService;
import cn.csu.mypetstore_springboot.Services.OrderService;
import cn.csu.mypetstore_springboot.domain.LineItem;
import cn.csu.mypetstore_springboot.domain.Order;
import cn.csu.mypetstore_springboot.enums.HeaderSelection;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin/order/details")
public class OrderDetailsViewController {
    private final LineItemService lineItemService;
    private final OrderService orderService;
    private final static int LINE_ITEMS_PER_PAGE = 16;

    public OrderDetailsViewController(LineItemService lineItemService, OrderService orderService) {
        this.lineItemService = lineItemService;
        this.orderService = orderService;
    }

    @RequestMapping("view/{orderId}")
    public String orderDetailsView(@PathVariable Long orderId, Model model) {
        Order orderByOrderId = orderService.getOrderByOrderId(orderId);
        model.addAttribute("order", orderByOrderId);
        model.addAttribute("pageTitle", "Order Details %d".formatted(orderId));
        model.addAttribute("headerSelection", HeaderSelection.Orders);
        return "admin/OrderDetails";
    }

    @RequestMapping("/view/table")
    public String getTable(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(value = "orderId") Long orderId,
            Model model
    ) {
        List<LineItem> lineItemsByOrderId = lineItemService.getLineItemsByOrderId(orderId, pageNumber, LINE_ITEMS_PER_PAGE);

        model.addAttribute("orderId", orderId);
        model.addAttribute("lineItemsList", lineItemsByOrderId);

        return "admin/OrderDetailsTable";
    }

    @RequestMapping("/view/{orderId}/totalPageNumber")
    public ResponseEntity<Long> totalPageNumber(@PathVariable Long orderId) {
        Long totalPageNumber = lineItemService.getTotalPageNumberByOrderId(orderId, LINE_ITEMS_PER_PAGE);

        return ResponseEntity.ok(totalPageNumber);
    }
}

package cn.csu.mypetstore_springboot.domain;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
public class LineItem implements Serializable {

    @Serial
    private static final long serialVersionUID = 6804536240033522156L;

    private int orderId;
    private int lineNumber;
    private int quantity;
    @Column(name = "product_id", insertable = false, updatable = false)
    private Long productId;
    private BigDecimal unitPrice;

    @ManyToOne
    private Product product;
    private BigDecimal total;
    @Id
    @GeneratedValue
    private Long id;

    public LineItem() {
    }

    public LineItem(int lineNumber, CartItem cartItem) {
        this.lineNumber = lineNumber;
        this.quantity = cartItem.getQuantity();
        this.productId = cartItem.getProduct().getId();
        this.unitPrice = cartItem.getProduct().getListPrice();
        this.product = cartItem.getProduct();
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long itemId) {
        this.productId = itemId;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitprice) {
        this.unitPrice = unitprice;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public Product getItem() {
        return product;
    }

    public void setItem(Product product) {
        this.product = product;
        calculateTotal();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        calculateTotal();
    }

    private void calculateTotal() {
        if (product != null && product.getListPrice() != null) {
            total = product.getListPrice().multiply(new BigDecimal(quantity));
        } else {
            total = null;
        }
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

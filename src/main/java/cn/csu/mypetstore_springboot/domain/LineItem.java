package cn.csu.mypetstore_springboot.domain;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
public class LineItem implements Serializable {

    @Serial
    private static final long serialVersionUID = 6804536240033522156L;

    @Column(name = "order_id")
    private Long orderId;
    private Integer quantity;
    @Column(name = "product_id")
    private Long productId;
    private BigDecimal unitPrice;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    @Id
    @GeneratedValue
    private Long id;

    public LineItem() {
    }

    public LineItem(CartItem cartItem) {
        this.quantity = cartItem.getQuantity();
        this.productId = cartItem.getProduct().getProductId();
        this.unitPrice = cartItem.getProduct().getListPrice();
        this.product = cartItem.getProduct();
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
        return product.getListPrice().multiply(new BigDecimal(quantity));
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}

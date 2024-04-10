package cn.csu.mypetstore_springboot.domain;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Product implements Serializable {

    @Serial
    private static final long serialVersionUID = -2159121673445254631L;

    private BigDecimal listPrice;
    private BigDecimal unitCost;
    private int supplierId;
    private String status;
    @ManyToOne
    @JoinColumn(name = "pet_breed_id")
    private PetBreed petBreed;
    private int quantity;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = ProductAttribute.class, mappedBy = "productId")
    private List<ProductAttribute> attributes;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    public Product() {

    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public PetBreed getPetBreed() {
        return petBreed;
    }

    public void setPetBreed(PetBreed petBreed) {
        this.petBreed = petBreed;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toString() {
        return "(" + getProductId() + "-" + petBreed.getId() + ")";
    }


    public List<ProductAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ProductAttribute> attributes) {
        this.attributes = attributes;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}

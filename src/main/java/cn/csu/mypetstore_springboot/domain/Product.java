package cn.csu.mypetstore_springboot.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
public class Product implements Serializable {

    @Serial
    private static final long serialVersionUID = -2159121673445254631L;

    private BigDecimal listPrice;
    private BigDecimal unitCost;
    private Integer supplierId;
    private String status;

    @Column(name = "pet_breed_id")
    private Long petBreedId;

    @ManyToOne
    @JoinColumn(name = "pet_breed_id", insertable = false, updatable = false)
    private PetBreed petBreed;
    private Integer quantity;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = ProductAttribute.class, mappedBy = "productId")
    private List<ProductAttribute> attributes;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    public Product() {

    }

    public static Product NoNullEntity() {
        Product product = new Product();
        PetBreed petBreed1 = PetBreed.NoNullEntity();
        product.setPetBreed(petBreed1);
        return product;
    }
}

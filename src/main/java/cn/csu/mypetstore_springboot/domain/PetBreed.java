package cn.csu.mypetstore_springboot.domain;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Table(name = "pet_breed")
@Entity
public class PetBreed implements Serializable {
    @Serial
    private static final long serialVersionUID = -7492639752670189553L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "name")
    private String name;
    private String description;
    private String imagePath;

    @ManyToOne
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private Category category;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        return getName();
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public static PetBreed NoNullEntity() {
        PetBreed petBreed = new PetBreed();
        Category category1 = new Category();
        petBreed.setCategory(category1);
        return petBreed;
    }
}

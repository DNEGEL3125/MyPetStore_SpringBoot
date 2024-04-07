package cn.csu.mypetstore_springboot.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serial;
import java.io.Serializable;

@Entity
public class Category implements Serializable {

    @Serial
    private static final long serialVersionUID = 3992469837058393712L;

    private String name;
    private String imagePath;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}

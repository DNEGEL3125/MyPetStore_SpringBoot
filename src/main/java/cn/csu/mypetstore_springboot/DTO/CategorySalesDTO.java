package cn.csu.mypetstore_springboot.DTO;

import java.math.BigDecimal;

public class CategorySalesDTO {
    private String categoryName;
    private BigDecimal sales;

    public CategorySalesDTO() {

    }


    public CategorySalesDTO(String categoryName, BigDecimal sales) {
        this.categoryName = categoryName;
        this.sales = sales;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public BigDecimal getSales() {
        return sales;
    }

    public void setSales(BigDecimal sales) {
        this.sales = sales;
    }
}

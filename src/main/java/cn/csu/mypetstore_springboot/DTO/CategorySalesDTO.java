package cn.csu.mypetstore_springboot.DTO;

public class CategorySalesDTO {
    private int timePeriod;
    private long sales;

    public CategorySalesDTO() {

    }

    // Constructor, getters, and setters
    public CategorySalesDTO(int timePeriod, long sales) {
        this.timePeriod = timePeriod;
        this.sales = sales;
    }

    public int getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(int timePeriod) {
        this.timePeriod = timePeriod;
    }

    public long getSales() {
        return sales;
    }

    public void setSales(long sales) {
        this.sales = sales;
    }
}

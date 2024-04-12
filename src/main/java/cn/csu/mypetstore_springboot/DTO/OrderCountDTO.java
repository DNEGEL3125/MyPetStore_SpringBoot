package cn.csu.mypetstore_springboot.DTO;

public class OrderCountDTO {
    private int timePeriod;
    private long orderCount;

    public OrderCountDTO() {

    }

    // Constructor, getters, and setters
    public OrderCountDTO(int timePeriod, long orderCount) {
        this.timePeriod = timePeriod;
        this.orderCount = orderCount;
    }

    public int getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(int timePeriod) {
        this.timePeriod = timePeriod;
    }

    public long getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(long orderCount) {
        this.orderCount = orderCount;
    }
}

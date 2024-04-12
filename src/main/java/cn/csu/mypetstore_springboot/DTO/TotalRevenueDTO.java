package cn.csu.mypetstore_springboot.DTO;

import java.math.BigDecimal;

public class TotalRevenueDTO {
    private int timePeriod;
    private BigDecimal totalRevenue;

    public TotalRevenueDTO() {

    }

    public TotalRevenueDTO(int timePeriod, BigDecimal totalRevenue) {
        this.timePeriod = timePeriod;
        this.totalRevenue = totalRevenue;
    }

    public int getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(int timePeriod) {
        this.timePeriod = timePeriod;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}

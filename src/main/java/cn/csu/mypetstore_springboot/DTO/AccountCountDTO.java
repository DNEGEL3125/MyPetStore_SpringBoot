package cn.csu.mypetstore_springboot.DTO;

public class AccountCountDTO {
    private int timePeriod;
    private long accountCount;

    public AccountCountDTO() {

    }

    // Constructor, getters, and setters
    public AccountCountDTO(int timePeriod, long accountCount) {
        this.timePeriod = timePeriod;
        this.accountCount = accountCount;
    }

    public int getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(int timePeriod) {
        this.timePeriod = timePeriod;
    }

    public long getAccountCount() {
        return accountCount;
    }

    public void setAccountCount(long accountCount) {
        this.accountCount = accountCount;
    }
}

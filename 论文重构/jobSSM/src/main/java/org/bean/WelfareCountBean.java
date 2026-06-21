package org.bean;

public class WelfareCountBean {
    private String welfare;
    private Integer welfare_count;
    
    public WelfareCountBean(String welfare, Integer welfare_count) {
        this.welfare = welfare;
        this.welfare_count = welfare_count;
    }
    
    public WelfareCountBean() {
    }
    
    public String getWelfare() {
        return welfare;
    }
    
    public void setWelfare(String welfare) {
        this.welfare = welfare;
    }
    
    public Integer getWelfare_count() {
        return welfare_count;
    }
    
    public void setWelfare_count(Integer welfare_count) {
        this.welfare_count = welfare_count;
    }
}

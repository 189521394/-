package org.bean;

public class CityCountBean {
    private String city;
    private Integer city_count;
    
    public CityCountBean() {
    }
    
    public CityCountBean(String city, Integer city_count) {
        this.city = city;
        this.city_count = city_count;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public Integer getCity_count() {
        return city_count;
    }
    
    public void setCity_count(Integer city_count) {
        this.city_count = city_count;
    }
}

package org.bean;

public class SalaryAvgBean {
    private String city;
    private Double salary_avg_city;
    
    public SalaryAvgBean() {
    }
    
    public SalaryAvgBean(String city, Double salary_avg_city) {
        this.city = city;
        this.salary_avg_city = salary_avg_city;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public Double getSalary_avg_city() {
        return salary_avg_city;
    }
    
    public void setSalary_avg_city(Double salary_avg_city) {
        this.salary_avg_city = salary_avg_city;
    }
}

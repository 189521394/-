package org.bean;

public class SalaryBean {
    private String salary_range;
    private Integer salary_count;
    
    public SalaryBean() {
    }
    
    public SalaryBean(String salary_range, Integer salary_count) {
        this.salary_range = salary_range;
        this.salary_count = salary_count;
    }
    
    public String getSalary_range() {
        return salary_range;
    }
    
    public void setSalary_range(String salary_range) {
        this.salary_range = salary_range;
    }
    
    public Integer getSalary_count() {
        return salary_count;
    }
    
    public void setSalary_count(Integer salary_count) {
        this.salary_count = salary_count;
    }
}

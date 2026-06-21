package org.bean;

public class DegreeCountBean {
    private String degree;
    private Integer degree_count;
    
    public DegreeCountBean() {
    }
    
    public DegreeCountBean(String degree, Integer degree_count) {
        this.degree = degree;
        this.degree_count = degree_count;
    }
    
    public String getDegree() {
        return degree;
    }
    
    public void setDegree(String degree) {
        this.degree = degree;
    }
    
    public Integer getDegree_count() {
        return degree_count;
    }
    
    public void setDegree_count(Integer degree_count) {
        this.degree_count = degree_count;
    }
}

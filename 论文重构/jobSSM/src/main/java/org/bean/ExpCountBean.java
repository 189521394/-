package org.bean;

public class ExpCountBean {
    private String exp;
    private Integer exp_count;
    
    public ExpCountBean() {
    }
    
    public ExpCountBean(String exp, Integer exp_count) {
        this.exp = exp;
        this.exp_count = exp_count;
    }
    
    public String getExp() {
        return exp;
    }
    
    public void setExp(String exp) {
        this.exp = exp;
    }
    
    public Integer getExp_count() {
        return exp_count;
    }
    
    public void setExp_count(Integer exp_count) {
        this.exp_count = exp_count;
    }
}

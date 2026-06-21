package org.bean;

public class JobNameBean {
    private String jobname;
    private Integer name_count;
    
    public JobNameBean() {
    }
    
    public JobNameBean(String jobname, Integer name_count) {
        this.jobname = jobname;
        this.name_count = name_count;
    }
    
    public String getJobname() {
        return jobname;
    }
    
    public void setJobname(String jobname) {
        this.jobname = jobname;
    }
    
    public Integer getName_count() {
        return name_count;
    }
    
    public void setName_count(Integer name_count) {
        this.name_count = name_count;
    }
}
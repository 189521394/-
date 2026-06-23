package org.bean;

public class SkillCountBean {
    private String skill;
    private Integer skill_count;
    
    public SkillCountBean() {
    }
    
    public SkillCountBean(String skill, Integer skill_count) {
        this.skill = skill;
        this.skill_count = skill_count;
    }
    
    public String getSkill() {
        return skill;
    }
    
    public void setSkill(String skill) {
        this.skill = skill;
    }
    
    public Integer getSkill_count() {
        return skill_count;
    }
    
    public void setSkill_count(Integer skill_count) {
        this.skill_count = skill_count;
    }
}

package org.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.bean.SkillCountBean;

import java.util.ArrayList;

@Mapper
public interface SkillMapper {
    @Select("select * from skill_count order by skill_count desc")
    public ArrayList<SkillCountBean> getSkillCountAll();
}

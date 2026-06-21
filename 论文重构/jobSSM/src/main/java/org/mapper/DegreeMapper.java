package org.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.bean.DegreeCountBean;

import java.util.ArrayList;

@Mapper
public interface DegreeMapper {
    @Select("select * from degree_count order by degree_count desc")
    public ArrayList<DegreeCountBean> getDegreeCountAll();
}

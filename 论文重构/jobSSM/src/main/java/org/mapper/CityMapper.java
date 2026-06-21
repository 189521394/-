package org.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.bean.CityCountBean;

import java.util.ArrayList;

@Mapper
public interface CityMapper {
    
    @Select("select * from city_count order by city_count desc")
    public ArrayList<CityCountBean> getCityCountAll();
}

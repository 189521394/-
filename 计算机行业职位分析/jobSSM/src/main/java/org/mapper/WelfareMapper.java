package org.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.bean.WelfareCountBean;

import java.util.ArrayList;

@Mapper
public interface WelfareMapper {
    @Select("select * from welfare_count order by welfare_count desc")
    public ArrayList<WelfareCountBean> getWelfareCountAll();
}

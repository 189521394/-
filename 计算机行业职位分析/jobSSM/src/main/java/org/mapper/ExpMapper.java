package org.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.bean.ExpCountBean;

import java.util.ArrayList;

@Mapper
public interface ExpMapper {
    @Select("select * from exp_count order by exp_count desc")
    public ArrayList<ExpCountBean> getExpCountAll();
}

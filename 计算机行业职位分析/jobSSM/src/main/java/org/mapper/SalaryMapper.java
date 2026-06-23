package org.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.bean.SalaryBean;

import java.util.ArrayList;

@Mapper
public interface SalaryMapper {
    @Select("select * from salary_count order by min_salary ASC")
    public ArrayList<SalaryBean> getSalaryAll();
}

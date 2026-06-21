package org.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.bean.SalaryAvgBean;

import java.util.ArrayList;

@Mapper
public interface SalaryAvgMapper {
    @Select("select * from salary_avg")
    public ArrayList<SalaryAvgBean> getSalaryAvgAll();
}

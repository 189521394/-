package org.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.bean.JobNameBean;

import java.util.ArrayList;

@Mapper
public interface JobNameMapper {
    @Select("select * from jobname_count")
    public ArrayList<JobNameBean> getJobNameAll();
}

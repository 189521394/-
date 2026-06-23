package org.service;

import org.bean.DataBean;
import org.bean.JobNameBean;
import org.mapper.JobNameMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JobNameService {
    @Autowired
    private JobNameMapper jobNameMapper;

    public DataBean[] getJobNameAll() {
        ArrayList<JobNameBean> jobNameAll = jobNameMapper.getJobNameAll();
        DataBean[] dataBeans = new DataBean[jobNameAll.size()];
        for (int i = 0; i < jobNameAll.size(); i++) {
            JobNameBean jobNameBean = jobNameAll.get(i);
            DataBean dataBean = new DataBean(jobNameBean.getJobname(), jobNameBean.getName_count() + "");
            dataBeans[i] = dataBean;
        }
        return dataBeans;
    }
}

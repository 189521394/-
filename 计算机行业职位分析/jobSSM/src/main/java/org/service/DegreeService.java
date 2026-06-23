package org.service;

import org.bean.DataBean;
import org.bean.DegreeCountBean;
import org.mapper.DegreeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DegreeService {
    @Autowired
    private DegreeMapper degreeMapper;

    public DataBean[] getDegreeCountAll() {
        ArrayList<DegreeCountBean> degreeCountAll = degreeMapper.getDegreeCountAll();
        DataBean[] dataBeans = new DataBean[degreeCountAll.size()];
        for (int i = 0; i < degreeCountAll.size(); i++) {
            DegreeCountBean degreeCountBean = degreeCountAll.get(i);
            DataBean dataBean = new DataBean(degreeCountBean.getDegree(), degreeCountBean.getDegree_count() + "");
            dataBeans[i] = dataBean;
        }
        return dataBeans;
    }
}

package org.service;

import org.bean.DataBean;
import org.bean.ExpCountBean;
import org.mapper.ExpMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ExpService {
    @Autowired
    private ExpMapper expMapper;

    public DataBean[] getExpCountAll() {
        ArrayList<ExpCountBean> expCountAll = expMapper.getExpCountAll();
        DataBean[] dataBeans = new DataBean[expCountAll.size()];
        for (int i = 0; i < expCountAll.size(); i++) {
            ExpCountBean expCountBean = expCountAll.get(i);
            DataBean dataBean = new DataBean(expCountBean.getExp(), expCountBean.getExp_count() + "");
            dataBeans[i] = dataBean;
        }
        return dataBeans;
    }
}

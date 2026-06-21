package org.service;

import org.bean.CityCountBean;
import org.bean.DataBean;
import org.mapper.CityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CityService {
    @Autowired
    private CityMapper cityMapper;
    
    public DataBean[] getCityCountAll() {
        ArrayList<CityCountBean> cityCountAll = cityMapper.getCityCountAll();
        DataBean[] dataBeans = new DataBean[cityCountAll.size()];
        for (int i = 0; i < cityCountAll.size(); i++) {
            CityCountBean cityCountBean = cityCountAll.get(i);
            DataBean dataBean = new DataBean(cityCountBean.getCity(), cityCountBean.getCity_count() + "");
            dataBeans[i] = dataBean;
        }
        return dataBeans;
    }
}

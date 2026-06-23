package org.service;

import org.bean.DataBean;
import org.bean.WelfareCountBean;
import org.mapper.WelfareMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class WelfareService {
    @Autowired
    private WelfareMapper welfareMapper;

    public DataBean[] getWelfareCountAll() {
        ArrayList<WelfareCountBean> welfareCountAll = welfareMapper.getWelfareCountAll();
        DataBean[] dataBeans = new DataBean[welfareCountAll.size()];
        for (int i = 0; i < welfareCountAll.size(); i++) {
            WelfareCountBean welfareCountBean = welfareCountAll.get(i);
            DataBean dataBean = new DataBean(welfareCountBean.getWelfare(), welfareCountBean.getWelfare_count() + "");
            dataBeans[i] = dataBean;
        }
        return dataBeans;
    }
}

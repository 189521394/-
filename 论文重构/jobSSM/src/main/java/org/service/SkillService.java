package org.service;

import org.bean.DataBean;
import org.bean.SkillCountBean;
import org.mapper.SkillMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SkillService {
    @Autowired
    private SkillMapper skillMapper;

    public DataBean[] getSkillCountAll() {
        ArrayList<SkillCountBean> skillCountAll = skillMapper.getSkillCountAll();
        DataBean[] dataBeans = new DataBean[skillCountAll.size()];
        for (int i = 0; i < skillCountAll.size(); i++) {
            SkillCountBean skillCountBean = skillCountAll.get(i);
            DataBean dataBean = new DataBean(skillCountBean.getSkill(), skillCountBean.getSkill_count() + "");
            dataBeans[i] = dataBean;
        }
        return dataBeans;
    }
}

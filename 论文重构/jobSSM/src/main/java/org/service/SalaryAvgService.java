package org.service;

import org.bean.SalaryAvgBean;
import org.mapper.SalaryAvgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SalaryAvgService {
    @Autowired
    private SalaryAvgMapper salaryAvgMapper;

    public Object[] getSalaryAvgAll() {
        ArrayList<SalaryAvgBean> salaryAvgAll = salaryAvgMapper.getSalaryAvgAll();
        if (salaryAvgAll == null) {
            salaryAvgAll = new ArrayList<>();
        }
        String[] city = new String[salaryAvgAll.size()];
        Double[] salaryAvg = new Double[salaryAvgAll.size()];
        for (int i = 0; i < salaryAvgAll.size(); i++) {
            SalaryAvgBean bean = salaryAvgAll.get(i);
            if (bean != null) {
                city[i] = bean.getCity();
                salaryAvg[i] = bean.getSalary_avg_city();
            }
        }
        return new Object[]{city, salaryAvg};
    }
}

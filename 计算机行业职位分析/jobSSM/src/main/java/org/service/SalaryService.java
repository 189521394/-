package org.service;

import org.bean.SalaryBean;
import org.mapper.SalaryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SalaryService {
    @Autowired
    private SalaryMapper salaryMapper;

    public Object[] getSalaryRangeAll() {
        ArrayList<SalaryBean> salaryAll = salaryMapper.getSalaryAll();
        Object[] objects = new Object[2];
        String[] salary_range = new String[salaryAll.size()];
        Integer[] salary_count = new Integer[salaryAll.size()];
        for (int i = 0; i < salaryAll.size(); i++) {
            SalaryBean salaryBean = salaryAll.get(i);
            salary_range[i] = salaryBean.getSalary_range();
            salary_count[i] = salaryBean.getSalary_count();
        }
        objects[0] = salary_range;
        objects[1] = salary_count;
        return objects;
    }
}

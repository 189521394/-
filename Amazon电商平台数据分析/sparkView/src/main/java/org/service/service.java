package org.service;

import org.DAO.DAO;
import org.bean.Top10Bean;
import org.bean.Top3Bean;
import org.bean.conversionBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class service {
    @Autowired
    private DAO dao;
    
    public Object[] getTop10() {
        Object[] objects = new Object[4];
        ArrayList<Top10Bean> top10 = dao.getTop10();
        String[] id = new String[top10.size()];
        Long[] view = new Long[top10.size()];
        Long[] cart = new Long[top10.size()];
        Long[] purchase = new Long[top10.size()];
        for (int i = 0; i < top10.size(); i++) {
            Top10Bean top10Bean = top10.get(i);
            id[i] = top10Bean.getId();
            view[i] = top10Bean.getView();
            cart[i] = top10Bean.getCart();
            purchase[i] = top10Bean.getPurchase();
        }
        objects[0] = id;
        objects[1] = view;
        objects[2] = cart;
        objects[3] = purchase;
        
        return objects;
    }
    
    public Object[] getTop3() {
        Object[] objects = new Object[4];
        ArrayList<Top3Bean> top3 = dao.getTop3();
        String[] name = new String[top3.size()];
        Long[] pro4767 = new Long[top3.size()];
        Long[] pro4856 = new Long[top3.size()];
        Long[] pro5115 = new Long[top3.size()];
        for (int i = 0; i < top3.size(); i++) {
            Top3Bean top3Bean = top3.get(i);
            name[i] = top3Bean.getName();
            pro4767[i] = top3Bean.getPro4767();
            pro4856[i] = top3Bean.getPro4856();
            pro5115[i] = top3Bean.getPro5115();
        }
        objects[0] = name;
        objects[1] = pro4767;
        objects[2] = pro4856;
        objects[3] = pro5115;

        return objects;
    }

    public Object[] getConversion() {
        Object[] objects = new Object[2];
        ArrayList<conversionBean> conversions = dao.getConversion();
        String[] jump = new String[conversions.size()];
        Double[] conversion = new Double[conversions.size()];
        for (int i = 0; i < conversions.size(); i++) {
            conversionBean bean = conversions.get(i);
            jump[i] = bean.getJump();
            conversion[i] = bean.getConversion();
        }
        objects[0] = jump;
        objects[1] = conversion;

        return objects;
    }
}

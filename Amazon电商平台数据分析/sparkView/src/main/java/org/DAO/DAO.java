package org.DAO;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.bean.Top10Bean;
import org.bean.Top3Bean;
import org.bean.conversionBean;
import org.cn.Connect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class DAO {
    @Autowired
    private Connect myConn;
    
    public ArrayList<Top10Bean> getTop10() {
        ArrayList<Top10Bean> top10Beans = new ArrayList<>();
        Connection conn = null;
        
        try {
            conn = myConn.init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Table categoryCount = null;
        try {
            categoryCount = conn.getTable(TableName.valueOf("categoryCount"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scan scan = new Scan();
        ResultScanner scanner = null;
        try {
            scanner = categoryCount.getScanner(scan);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Iterator<Result> result = scanner.iterator();
        while (result.hasNext()) {
            Result r = result.next();
            Top10Bean top10Bean = new Top10Bean();
            List<Cell> cells = r.listCells();
            for (int i = 0; i < cells.size(); i++) {
                Cell cell = cells.get(i);
                if (i == 0) top10Bean.setId(Bytes.toString(CellUtil.cloneRow(cell)));
                if (i == 0) top10Bean.setCart(Bytes.toLong(CellUtil.cloneValue(cell)));
                if (i == 1) top10Bean.setPurchase(Bytes.toLong(CellUtil.cloneValue(cell)));
                if (i == 2) top10Bean.setView(Bytes.toLong(CellUtil.cloneValue(cell)));
            }
            top10Beans.add(top10Bean);
        }
        try {
            conn.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return top10Beans;
    }
    
    public ArrayList<Top3Bean> getTop3() {
        ArrayList<Top3Bean> top3Beans = new ArrayList<>();
        Connection conn = null;
        
        try {
            conn = myConn.init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Table top3 = null;
        try {
            top3 = conn.getTable(TableName.valueOf("top3"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scan scan = new Scan();
        ResultScanner scanner = null;
        try {
            scanner = top3.getScanner(scan);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Iterator<Result> result = scanner.iterator();
        while (result.hasNext()) {
            Result r = result.next();
            Top3Bean top3Bean = new Top3Bean();
            List<Cell> cells = r.listCells();
            for (int i = 0; i < cells.size(); i++) {
                Cell cell = cells.get(i);
                if (i == 0) top3Bean.setName(Bytes.toString(CellUtil.cloneRow(cell)));
                if (i == 0) top3Bean.setPro4767(Bytes.toLong(CellUtil.cloneValue(cell)));
                if (i == 1) top3Bean.setPro4856(Bytes.toLong(CellUtil.cloneValue(cell)));
                if (i == 2) top3Bean.setPro5115(Bytes.toLong(CellUtil.cloneValue(cell)));
            }
            top3Beans.add(top3Bean);
        }
        try {
            conn.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return top3Beans;
    }
    
    public ArrayList<conversionBean> getConversion() {
        ArrayList<conversionBean> conversionBeans = new ArrayList<>();
        Connection conn = null;

        try {
            conn = myConn.init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Table conversion = null;
        try {
            conversion = conn.getTable(TableName.valueOf("conversion"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scan scan = new Scan();
        ResultScanner scanner = null;
        try {
            scanner = conversion.getScanner(scan);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Iterator<Result> result = scanner.iterator();
        while (result.hasNext()) {
            Result r = result.next();
            conversionBean bean = new conversionBean();
            List<Cell> cells = r.listCells();
            for (int i = 0; i < cells.size(); i++) {
                Cell cell = cells.get(i);
                if (i == 0) bean.setJump(Bytes.toString(CellUtil.cloneRow(cell)));
                if (i == 0) bean.setConversion(Bytes.toDouble(CellUtil.cloneValue(cell)));
            }
            conversionBeans.add(bean);
        }
        try {
            conn.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return conversionBeans;
    }
}

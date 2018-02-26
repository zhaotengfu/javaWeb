package com.ztf.core.ireport.entity;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018-1-15.
 */
public class ChartDataSource implements JRDataSource {

    private int index;

    private List<Bar> dataList;


    public ChartDataSource(List<Bar> dataList){
        this.index = -1;
        this.dataList = dataList;
    }

    public ChartDataSource(List<Map<String,Object>> list,int index){
        this.index = -1;
        List<Bar> data = new ArrayList<Bar>();
        for(Map map:list){
            Bar bar = new Bar();
            bar.setCategory(map.get("CATEGORY")+"");
            bar.setSeries(map.get("SERIES")+"");
            bar.setValue(Float.parseFloat(map.get("VALUE")+""));
            bar.setDate(map.get("date")+"");
            bar.setName(map.get("STAPEO")+"");
            data.add(bar);
        }
        this.dataList = data;

    }

    @Override
    public boolean next() throws JRException {
        index++;
        return (index < dataList.size());
    }

    @Override
    public Object getFieldValue(JRField jrField) throws JRException {
        Object o = null;
        String sName = jrField.getName();
        Bar result = dataList.get(index);
        if(result == null)
        return null;
        if(sName.equals("series")){
            o = result.getSeries();
        }else if(sName.equals("value")){
            o = result.getValue();
        }else if(sName.equals("category")){
            o = result.getCategory();
        }
        return  o;
    }
}


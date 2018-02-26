package com.ztf.core.ireport.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018-1-15.
 */
public class Bar {
   private String series;

   private Float value;

   private String category;

   private String date;

   private String name;


   public List<Bar> getBarList(List<Map<String,Object>> list){
       List<Bar> barList = new ArrayList<Bar>();
       for(Map map:list){
           Bar bar = new Bar();
           bar.setCategory(map.get("CATEGORY")+"");
           bar.setSeries(map.get("SERIES")+"");
           bar.setValue(Float.parseFloat(map.get("VALUE")+""));
           bar.setDate(map.get("date")+"");
           bar.setName(map.get("STAPEO")+"");
           barList.add(bar);
       }
       return barList;

   }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

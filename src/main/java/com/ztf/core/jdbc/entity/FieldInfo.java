package com.ztf.core.jdbc.entity;

public class FieldInfo {
    //java字段名
    private String pojoFieldName;
    //数据库字段名
    private String dbFieldName;

    //属性
    private Class<?> type;
    //是否是主键
    private boolean isPk = false;
    //update时是否需要更新
    private boolean isUpdate = true;
    //insert时是否需要插入
    private boolean isInsert = true;

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public String getPojoFieldName() {
        return pojoFieldName;
    }

    public void setPojoFieldName(String pojoFieldName) {
        this.pojoFieldName = pojoFieldName;
    }

    public String getDbFieldName() {
        return dbFieldName;
    }

    public void setDbFieldName(String dbFieldName) {
        this.dbFieldName = dbFieldName;
    }

    public boolean isPk() {
        return isPk;
    }

    public void setPk(boolean pk) {
        isPk = pk;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }

    public boolean isInsert() {
        return isInsert;
    }

    public void setInsert(boolean insert) {
        isInsert = insert;
    }
}
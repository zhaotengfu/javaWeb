package com.ztf.core.jdbc.utils;



import com.ztf.core.jdbc.annotation.Table;
import com.ztf.core.jdbc.entity.FieldInfo;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ztf on 2018-1-16.
 */
public class SQLUtils {

    private static Map cacheMap = new HashMap();
    private static Map insertSqlCache = new HashMap();
    private static Map updateSqlCache = new HashMap();
    private static Map deleteSqlCache = new HashMap();
    private static Map selectSqlCache = new HashMap();

    /**
     * 根据pojo类的class来构建select * from 的SQL语句
     * @param pojoClass
     * @return
     */
    public static String buildSelectSql(Class pojoClass){
        List<FieldInfo> fieldInfoList = loadPojoSqlInfo(pojoClass);
        String sql = buildSelectSql(pojoClass, fieldInfoList);
        return sql;
    }

    /**
     * 根据pojo类的class来构建insert的SQL语句
     * @param pojoClass
     * @return
     */
    public static String buildInsertSql(Class pojoClass){
        List<FieldInfo> fieldInfoList = loadPojoSqlInfo(pojoClass);
        String sql = buildInsertSql(pojoClass, fieldInfoList);
        return sql;
    }

    /**
     * 根据pojo类的class构建根据pk来update的SQL语句
     * @param pojoClass
     * @return
     */
    public static String buildUpdateSql(Class pojoClass){
        List<FieldInfo> fieldInfoList = loadPojoSqlInfo(pojoClass);
        String sql = buildUpdateSqlByPK(pojoClass, fieldInfoList);
        return sql;
    }

    /**
     * 根据pojo类的Class和更新的条件字段来生成upate的SQL语句
     * @param pojoClass
     * @param columns
     * @return
     * @throws Exception
     */
    public static String buildUpdateSqlByColumns(Class pojoClass,String[] columns) throws Exception{
        if(null!=columns && columns.length>0){
            List<FieldInfo> fieldInfoList = loadPojoSqlInfo(pojoClass);
            String sql = buildUpdateSqlByColumns(pojoClass, fieldInfoList, columns);

            return sql;
        }else{
            throw new Exception("参数columns必须有值！");
        }
    }

    /**
     * 根据pojo类的Class生成根据pk来delete的SQL语句
     * @param pojoClass
     * @return
     */
    public static String buildDeleteSql(Class pojoClass){
        List<FieldInfo> fieldInfoList = loadPojoSqlInfo(pojoClass);
        String sql = buildDeleteSqlByPK(pojoClass,fieldInfoList);
        return sql;
    }

    /**
     * 根据pojo类的Class和更新的条件字段来生成delete的SQL语句
     * @param pojoClass
     * @param columns
     * @return
     * @throws Exception
     */
    public static String buildDeleteSqlByColumns(Class pojoClass,String[] columns) throws Exception{
        if(null!=columns && columns.length>0){
            List<FieldInfo> fieldInfoList = loadPojoSqlInfo(pojoClass);
            String sql = buildDeleteSqlByColumns(pojoClass, fieldInfoList, columns);
            return sql;
        }else{
            throw new Exception("参数columns必须有值！");
        }
    }

    /**
     * 将SQL查询出来的map对象转成实体对象
     * @param map
     * @param pojoClass
     * @return
     * @throws Exception
     */
    public static Object coverMapToBean(Map map,Class pojoClass) throws Exception{
        Object result = pojoClass.newInstance();

        List<FieldInfo> list = loadPojoSqlInfo(pojoClass);
        for(FieldInfo fieldInfo : list){
            String dbName = fieldInfo.getDbFieldName().toUpperCase();
            String fieldName = fieldInfo.getPojoFieldName();
            String setMethoName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            if(map.get(dbName)!=null){
                Method m = pojoClass.getMethod(setMethoName, fieldInfo.getType());
                m.invoke(result, map.get(dbName));
            }
        }
        return result;
    }

    /**
     * 将SQL查询出来的map对象转成实体对象
     * @param mapList
     * @param pojoClass
     * @return
     * @throws Exception
     */
    public static <T>List<T> coverListToBean(List<Map> mapList,Class<T> pojoClass) throws Exception{
       T result = pojoClass.newInstance();
        List<FieldInfo> list = loadPojoSqlInfo(pojoClass);
        List<T> listEntity = new ArrayList<T>();
        for(Map map:mapList){
            for(FieldInfo fieldInfo : list){
                String dbName = fieldInfo.getDbFieldName().toUpperCase();
                String fieldName = fieldInfo.getPojoFieldName();
                String setMethoName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                if(map.get(dbName)!=null){
                    Method m = pojoClass.getMethod(setMethoName, fieldInfo.getType());
                    System.out.println("map.get(dbName)="+map.get(dbName));
                    m.invoke(result, map.get(dbName));
                }
            }
            listEntity.add(result);
        }

        return listEntity;
    }



    /**
     * 加载读取pojo的注解信息
     * @param pojoClass
     * @return
     */
    @SuppressWarnings("unchecked")
    private static List<FieldInfo> loadPojoSqlInfo(Class pojoClass){
        List<FieldInfo> resultList = null;
        if(null == cacheMap.get(pojoClass.getName())){
            resultList = new ArrayList<FieldInfo>();
            Field[] fields = pojoClass.getDeclaredFields();
            for(Field field : fields){
                FieldInfo fieldInfo = new FieldInfo();
                fieldInfo.setPojoFieldName(field.getName());

                if(field.isAnnotationPresent(com.ztf.core.jdbc.annotation.Field.class)){
                    String value = ((com.ztf.core.jdbc.annotation.Field)field.getAnnotation(com.ztf.core.jdbc.annotation.Field.class)).value();//得到配置的数据库字段名
                    if(StringUtils.isEmpty(value)){//没有设置数据库的字段名，则取pojo的字段名
                        fieldInfo.setDbFieldName(lowerStrToUnderline(field.getName()));
                    }else{
                        fieldInfo.setDbFieldName(value);
                    }
                }else{
                    fieldInfo.setDbFieldName(lowerStrToUnderline(field.getName()));
                }

                if(field.isAnnotationPresent(com.ztf.core.jdbc.annotation.PK.class)){
                    fieldInfo.setPk(true);
                }
                if(field.isAnnotationPresent(com.ztf.core.jdbc.annotation.NoInsert.class)){
                    fieldInfo.setInsert(false);
                }
                if(field.isAnnotationPresent(com.ztf.core.jdbc.annotation.NoUpdate.class)){
                    fieldInfo.setUpdate(false);
                }

                fieldInfo.setType(field.getType());

                resultList.add(fieldInfo);
            }
            cacheMap.put(pojoClass.getName(), resultList);
        }else{
            resultList = (List<FieldInfo>)cacheMap.get(pojoClass.getName());
        }

        return resultList;
    }

    /**
     * 评价select语句
     * @param pojoClass
     * @param fieldInfoList
     * @return
     */
    private static String buildSelectSql(Class pojoClass,List<FieldInfo> fieldInfoList){
        if(selectSqlCache.get(pojoClass.getName()) != null){
            return (String)selectSqlCache.get(pojoClass.getName());
        }
        return "select * from " + loadTableName(pojoClass);
    }

    /**
     * 拼接insert的SQL
     * @param pojoClass
     * @param fieldInfoList
     * @return
     */
    @SuppressWarnings("unchecked")
    private static String buildInsertSql(Class pojoClass,List<FieldInfo> fieldInfoList){
        String result = null;
        if(insertSqlCache.get(pojoClass.getName()) != null){
            result = (String)insertSqlCache.get(pojoClass.getName());
            return result;
        }

        String tableName = loadTableName(pojoClass);

        StringBuffer temp1 = new StringBuffer();
        StringBuffer temp2 = new StringBuffer();
        for(FieldInfo fieldInfo : fieldInfoList){
            if(fieldInfo.isInsert()){
                temp1.append(fieldInfo.getDbFieldName()).append(",");
                temp2.append(":").append(fieldInfo.getPojoFieldName()).append(",");
            }
        }
        temp1.deleteCharAt(temp1.length()-1);
        temp2.deleteCharAt(temp2.length()-1);

        StringBuffer resultSql = new StringBuffer();
        resultSql.append("insert into ");
        resultSql.append(tableName);
        resultSql.append("(");
        resultSql.append(temp1);
        resultSql.append(") values (");
        resultSql.append(temp2);
        resultSql.append(")");

        result = resultSql.toString();
        insertSqlCache.put(pojoClass.getName(), result);
        return result;
    }

    /**
     * 生成根据主键生成删除的SQL
     * @param pojoClass
     * @param fieldInfoList
     * @return
     */
    @SuppressWarnings("unchecked")
    private static String buildDeleteSqlByPK(Class pojoClass,List<FieldInfo> fieldInfoList){
        String result = null;
        if(deleteSqlCache.get(pojoClass.getName()+"_pk") != null){
            result = (String)deleteSqlCache.get(pojoClass.getName());
            return result;
        }

        StringBuffer resultSql = new StringBuffer();
        resultSql.append(appendBaseDeleteSQL(pojoClass));

        for(FieldInfo fieldInfo : fieldInfoList){
            if(fieldInfo.isPk()){
                resultSql.append(fieldInfo.getDbFieldName());
                resultSql.append("=:").append(fieldInfo.getPojoFieldName()).append(" and ");
            }
        }
        resultSql.delete(resultSql.length()-4, resultSql.length());
        result = resultSql.toString();
        deleteSqlCache.put(pojoClass.getName()+"_pk", result);

        return result;
    }

    /**
     * 拼接根据主键来update的SQL
     * @param pojoClass
     * @param fieldInfoList
     * @return
     */
    @SuppressWarnings("unchecked")
    private static String buildUpdateSqlByPK(Class pojoClass, List<FieldInfo> fieldInfoList){
        String result = null;
        if(updateSqlCache.get(pojoClass.getName()+"_pk") != null){
            result = (String)updateSqlCache.get(pojoClass.getName()+"_pk");
            return result;
        }

        StringBuffer resultSql = new StringBuffer();
        resultSql.append(appendBaseUpdateSQL(pojoClass, fieldInfoList));

        for(FieldInfo fieldInfo : fieldInfoList){
            if(fieldInfo.isPk()){
                resultSql.append(fieldInfo.getDbFieldName());
                resultSql.append("=:").append(fieldInfo.getPojoFieldName()).append(" and ");
            }
        }
        resultSql.delete(resultSql.length()-4, resultSql.length());
        result = resultSql.toString();
        updateSqlCache.put(pojoClass.getName()+"_pk", result);

        return result;
    }

    /**
     * 根据用户指定的更新条件(字段)来生成update的SQL
     * @param pojoClass
     * @param fieldInfoList
     * @param columns
     * @return
     */
    private static String buildUpdateSqlByColumns(Class pojoClass, List<FieldInfo> fieldInfoList,String[] columns){
        StringBuffer resultSql = new StringBuffer();
        if(updateSqlCache.get(pojoClass.getName()+"_columns") != null){
            resultSql.append((String)updateSqlCache.get(pojoClass.getName()+"_columns"));
        }else{
            resultSql.append(appendBaseUpdateSQL(pojoClass, fieldInfoList));
        }

        for(String column : columns){
            for(FieldInfo fieldInfo : fieldInfoList){
                if(column.equals(fieldInfo.getPojoFieldName())){
                    resultSql.append(fieldInfo.getDbFieldName());
                    resultSql.append("=:").append(column).append(" and ");
                    break;
                }
            }
        }
        resultSql.delete(resultSql.length()-4, resultSql.length());
        return resultSql.toString();
    }

    /**
     * 拼接update语句的where之前的sql
     * @param pojoClass
     * @param fieldInfoList
     */
    @SuppressWarnings("unchecked")
    private static String appendBaseUpdateSQL(Class pojoClass, List<FieldInfo> fieldInfoList){
        String result = null;
        if(updateSqlCache.get(pojoClass.getName()+"_columns") != null){
            result = (String)updateSqlCache.get(pojoClass.getName()+"_columns");
        }else{
            StringBuffer resultSql = new StringBuffer();
            String tableName = loadTableName(pojoClass);

            resultSql.append("update ").append(tableName).append(" set ");
            for(FieldInfo fieldInfo : fieldInfoList){
                if(fieldInfo.isUpdate() && !fieldInfo.isPk()){
                    resultSql.append(fieldInfo.getDbFieldName());
                    resultSql.append("=:").append(fieldInfo.getPojoFieldName()).append(",");
                }
            }
            resultSql.deleteCharAt(resultSql.length()-1);
            resultSql.append(" where ");

            result = resultSql.toString();
            updateSqlCache.put(pojoClass.getName()+"_columns", result);
        }
        return result;
    }

    /**
     * 根据用户指定的更新条件(字段)来生成delete的SQL
     * @param pojoClass
     * @param fieldInfoList
     * @param columns
     * @return
     */
    private static String buildDeleteSqlByColumns(Class pojoClass, List<FieldInfo> fieldInfoList,String[] columns){
        StringBuffer resultSql = new StringBuffer();
        if(deleteSqlCache.get(pojoClass.getName()+"_columns") != null){
            resultSql.append((String)deleteSqlCache.get(pojoClass.getName()+"_columns"));
        }else{
            resultSql.append(appendBaseUpdateSQL(pojoClass, fieldInfoList));
        }

        for(String column : columns){
            for(FieldInfo fieldInfo : fieldInfoList){
                if(column.equals(fieldInfo.getPojoFieldName())){
                    resultSql.append(fieldInfo.getDbFieldName());
                    resultSql.append("=:").append(column).append(" and ");
                    break;
                }
            }
        }
        resultSql.delete(resultSql.length()-4, resultSql.length());
        return resultSql.toString();
    }

    /**
     * 拼接delete语句的where之前的sql
     * @param pojoClass
     */
    @SuppressWarnings("unchecked")
    private static String appendBaseDeleteSQL(Class pojoClass){
        if(deleteSqlCache.get(pojoClass.getName()+"_columns") != null){
            return (String)deleteSqlCache.get(pojoClass.getName()+"_columns");
        }else{
            String result = "delete from " + loadTableName(pojoClass) + " where ";
            deleteSqlCache.put(pojoClass.getName()+"_columns", result);
            return result;
        }
    }

    /**
     * 通过类获取表名
     * @param pojoClass
     * @return
     */
    @SuppressWarnings("unchecked")
    private static String loadTableName(Class pojoClass){
        if(pojoClass.isAnnotationPresent(Table.class)){
            Table table = (Table)pojoClass.getAnnotation(Table.class);
            System.out.println("fontColor="+table.fontColor());
            return table.value();
        }else{
            return lowerStrToUnderline(pojoClass.getSimpleName());
        }
    }

    /**
     * 将大写字母转换成下划线加小写字母
     * 例:userName--> user_name
     * @param str
     * @return
     */
    private static String lowerStrToUnderline(String str) {
        if(StringUtils.isEmpty(str)){
            return "";
        }
        StringBuilder sb = new StringBuilder(str);
        char c;
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                sb.replace(i+count, i+count+1, (c+"").toLowerCase());
                sb.insert(i+count, "_");
                count++;
            }
        }
        return sb.toString();
    }
}

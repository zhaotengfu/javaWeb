package com.ztf.core.jdbc.dao;



import com.ztf.core.jdbc.entity.ConditionDef;
import com.ztf.core.jdbc.entity.UserEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ztf on 2018-1-16.
 */
public class TestDao extends BaseDao{
    private ConditionDef conditionDef=new ConditionDef(
            new Object[][] {
                    {"id = :id"}
            }
    );

    public List query(Map paramMap){
        return this.baseQueryForList(UserEntity.class, conditionDef, paramMap);
    }

    public void save(UserEntity user){
        this.baseSave(user);
    }

    public void update() throws Exception{
        Map paramMap = new HashMap();
        paramMap.put("id", 123123);
        Object o = this.baseQueryForEntity(UserEntity.class, conditionDef, paramMap);
        UserEntity dept = null;
        if(o!=null){
            dept = (UserEntity)o;
            dept.setPassword("123123123123");
            this.baseSaveUpdate(dept);
        }
    }

    public void delete(UserEntity userEntity){
        this.baseDelete(userEntity);
    }
}

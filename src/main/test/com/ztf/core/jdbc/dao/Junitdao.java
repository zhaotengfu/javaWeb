package com.ztf.core.jdbc.dao;



import com.ztf.core.jdbc.entity.UserEntity;
import com.ztf.core.jdbc.utils.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ztf on 2018-1-16.
 *
 */

public class Junitdao {
    @Test
    public void testQuery(){
        TestDao testDao = new TestDao();
        Map map = new HashMap();
        map.put("id",29);
        List<Map> list = testDao.query(map);
        Map mm = list.get(0);
        List<UserEntity> lsitUser = null;
        try {
          Object obj =  SQLUtils.coverMapToBean(mm,UserEntity.class);
            lsitUser = SQLUtils.coverListToBean(list, UserEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals(1,lsitUser.size());
    }

    @Test
    public void testSave(){
        TestDao testDao = new TestDao();
        UserEntity userEntity = new UserEntity();
        userEntity.setId(123456);
        userEntity.setUsername("123123132");
        userEntity.setPassword("1213456789");
        userEntity.setLocked("1");
        userEntity.setSalt("123123123132");
        testDao.save(userEntity);
        Map map = new HashMap();
        map.put("id",123456);
        List<Map> list = testDao.query(map);
        Assert.assertEquals(1,list.size());
    }

    @Test
    public void  testDelete(){
        TestDao testDao = new TestDao();
        UserEntity userEntity = new UserEntity();
        userEntity.setId(123456);
        userEntity.setUsername("123123132");
        userEntity.setPassword("1213456789");
        userEntity.setLocked("1");
        userEntity.setSalt("123123123132");
        testDao.delete(userEntity);
        Map map = new HashMap();
        map.put("id",123456);
        List<Map> list = testDao.query(map);
        Assert.assertEquals(0,list.size());
    }

}

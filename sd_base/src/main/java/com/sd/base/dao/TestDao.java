package com.sd.base.dao;

import java.util.List;

import com.sd.base.bean.TestUserBean;

public class TestDao extends BaseDao{
    
    public List<TestUserBean> testDao(){
        return getSqlSession().selectList("testSqlMap.testQuery");
    }
}

package com.sd.base.service;

import java.util.List;

import com.sd.base.bean.TestUserBean;
import com.sd.base.dao.TestDao;

public class TestService {
    private TestDao testDao = null;

    public TestDao getTestDao() {
        return testDao;
    }

    public void setTestDao(TestDao testDao) {
        this.testDao = testDao;
    }
    
    
    public List<TestUserBean> testService(){
        return testDao.testDao();
    }
    
}

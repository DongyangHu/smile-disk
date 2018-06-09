package com.sd.fm.dao;

import java.util.HashMap;

import org.mybatis.spring.support.SqlSessionDaoSupport;

public class BaseDao extends SqlSessionDaoSupport{

    public BaseDao() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * 获取分页查询的参数
     * @param pageNo 当前页码
     * @param pageSize 每页条数
     * @return
     */
    public HashMap<String, Object> getPageQueryParams(int pageNo, int pageSize){
        HashMap<String, Object> params = new HashMap<String, Object>();
        int start = (pageNo - 1) * pageSize;
        params.put("start", Integer.valueOf(start));
        params.put("size", Integer.valueOf(pageSize));
        return params;
    }
    
}

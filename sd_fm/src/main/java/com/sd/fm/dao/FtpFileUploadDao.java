package com.sd.fm.dao;

import com.sd.fm.bean.SdFileBean;

public class FtpFileUploadDao extends BaseDao {
    
    /**
     * 上传文件
     * @param bean
     */
    public void uploadFile(SdFileBean bean) {
        this.getSqlSession().insert("fileInfo.saveFile", bean);
    }
    /**
     * 通过fileId查询单条文件信息
     * @param fileId
     * @return
     */
    public SdFileBean getFileInfoById(String fileId) {
        return this.getSqlSession().selectOne("fileInfo.getFileInfoById", fileId);
    }
}

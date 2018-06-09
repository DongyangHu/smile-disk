package com.sd.fm.dao;

import java.util.HashMap;
import java.util.List;

import com.sd.fm.bean.SdFileBean;
import com.sd.fm.bean.SdFileDirBean;

public class SdFileInfoDao extends BaseDao{
    
    /**
     * 创建文件夹
     * @param bean
     */
    public void createDir(SdFileDirBean bean) {
        this.getSqlSession().insert("fileInfo.createDir", bean);
    }
    
    /**
     * 根据id查询单个文件夹信息
     * @return
     */
    public SdFileDirBean getFileDirInfoById(String dirId) {
        return this.getSqlSession().selectOne("fileInfo.getFileDirInfoById", dirId);
    }
    
    /**
     * 根据父节点和用户查询其下的文件夹
     * @param parentDirId 父节点
     * @param userId 用户id
     * @param nameSign 文件夹名
     * @param pageNo 页码
     * @param pageSize 每页条数
     * @return
     */
    public List<SdFileDirBean> getFileDirPageList(String parentDirId, String userId, String nameSign, int pageNo, int pageSize){
        HashMap<String, Object> params = getPageQueryParams(pageNo, pageSize);
        if(null != parentDirId && !"".equals(parentDirId)) {
            params.put("parentDirId", parentDirId);
        }
        if(null != userId && !"".equals(userId)) {
            params.put("userId", userId);
        }
        if(null != nameSign && !"".equals(nameSign)) {
            params.put("nameSign", "%" + nameSign + "%");
        }
        
        return getSqlSession().selectList("fileInfo.getFileDirPageList", params);
    }
    /**
     * 根据父节点和用户查询其下的文件夹总条数
     * @param parentDirId
     * @param userId
     * @param nameSign
     * @return
     */
    public int getFileDirListCount(String parentDirId, String userId, String nameSign) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        if(null != parentDirId && !"".equals(parentDirId)) {
            params.put("parentDirId", parentDirId);
        }
        if(null != userId && !"".equals(userId)) {
            params.put("userId", userId);
        }
        if(null != nameSign && !"".equals(nameSign)) {
            params.put("nameSign", "%" + nameSign + "%");
        }
        
        return getSqlSession().selectOne("fileInfo.getFileDirListCount", params);
    }
    
    /**
     * 根据父节点和用户查询其下的文件夹
     * @param parentDirId
     * @param userId
     * @param nameSign
     * @param pageNo
     * @param pageSize
     * @return
     */
    public List<SdFileDirBean> getFileDirList(String parentDirId, String userId, String nameSign){
        HashMap<String, Object> params = new HashMap<String, Object>();
        if(null != parentDirId && !"".equals(parentDirId)) {
            params.put("parentDirId", parentDirId);
        }
        if(null != userId && !"".equals(userId)) {
            params.put("userId", userId);
        }
        if(null != nameSign && !"".equals(nameSign)) {
            params.put("nameSign", "%" + nameSign + "%");
        }
        
        return getSqlSession().selectList("fileInfo.getFileDirList", params);
    }
    /**
     * 每个文件夹对应的子节点数
     * @param parentDirId
     * @param userId
     * @param nameSign
     * @return
     */
    public List<HashMap<String, Object>> getFileDirChildCount(String parentDirId, String userId, String nameSign){
        HashMap<String, Object> params = new HashMap<String, Object>();
        if(null != parentDirId && !"".equals(parentDirId)) {
            params.put("parentDirId", parentDirId);
        }
        if(null != userId && !"".equals(userId)) {
            params.put("userId", userId);
        }
        if(null != nameSign && !"".equals(nameSign)) {
            params.put("nameSign", "%" + nameSign + "%");
        }
        
        return getSqlSession().selectList("fileInfo.getFileDirChildCount", params);
    }
    
    /**
     * 查询文件夹下的文件数，包括其下子节点
     * @param userId
     * @param fileName
     * @param parentDirId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public List<SdFileBean> getFileList(String userId, String fileName, String parentDirId, String fileType, String status, int pageNo, int pageSize){
        HashMap<String, Object> params = getPageQueryParams(pageNo, pageSize);
        
        if(null != parentDirId && !"".equals(parentDirId)) {
            params.put("parentDirId", parentDirId);
        }
        if(null != userId && !"".equals(userId)) {
            params.put("userId", userId);
        }
        if(null != fileName && !"".equals(fileName)) {
            params.put("fileName", "%" + fileName + "%");
        }
        if(null != fileType && !"".equals(fileType)) {
            params.put("fileType", fileType);
        }
        if(null != status && !"".equals(status)) {
            params.put("status", status);
        }
        
        return getSqlSession().selectList("fileInfo.getFileList", params);
    }
    
    /**
     * 查询文件夹下的文件总条数
     * @param userId
     * @param fileName
     * @param parentDirId
     * @return
     */
    public int getFileListCount(String userId, String fileName, String parentDirId, String fileType, String status){
        HashMap<String, Object> params = new HashMap<String, Object>();
        
        if(null != parentDirId && !"".equals(parentDirId)) {
            params.put("parentDirId", parentDirId);
        }
        if(null != userId && !"".equals(userId)) {
            params.put("userId", userId);
        }
        if(null != fileName && !"".equals(fileName)) {
            params.put("fileName", "%" + fileName + "%");
        }
        if(null != fileType && !"".equals(fileType)) {
            params.put("fileType", fileType);
        }
        if(null != status && !"".equals(status)) {
            params.put("status", status);
        }
        
        return getSqlSession().selectOne("fileInfo.getFileListCount", params);
    }
    
    /**
     * 根据id删除文件夹(临时)
     * @param dirId
     */
    public int deleteFileDirById(String dirId) {
        return this.getSqlSession().update("fileInfo.deleteFileDirById", dirId);
    }
    
    /**
     * 根据id删除文件(临时)
     * @param fileId
     * @return
     */
    public int deleteFileById(String fileId) {
        return this.getSqlSession().update("fileInfo.deleteFileById", fileId);
    }
    
    /**
     * 根据id恢复文件()
     * @param fileId
     * @return
     */
    public int recoverFileById(String fileId) {
        return this.getSqlSession().update("fileInfo.recoverFileById", fileId);
    }
    /**
     * 根据id删除文件(物理)
     * @param fileId
     * @return
     */
    public int deleteFileByIdPhysical(String fileId) {
        return this.getSqlSession().delete("fileInfo.deleteFileByIdPhysical", fileId);
    }
    
    /**
     * 文件重命名
     * @param fileId
     * @param newFileName
     * @return
     */
    public int updateFileName(String fileId, String newFileName) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("fileId", fileId);
        params.put("newFileName", newFileName);
        return this.getSqlSession().update("fileInfo.updateFileName", params);
    }
    
    /**
     * 文件夹重命名
     * @param fileId
     * @param newFileName
     * @return
     */
    public int updateFileDirName(String dirId, String dirName) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("dirId", dirId);
        params.put("dirName", dirName);
        return this.getSqlSession().update("fileInfo.updateFileDirName", params);
    }
    
    
    
}

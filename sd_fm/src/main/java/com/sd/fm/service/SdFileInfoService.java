package com.sd.fm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sd.common.redis.RedisManager;
import com.sd.common.time.SdDateUtil;
import com.sd.common.time.SdTime;
import com.sd.fm.bean.SdFileBean;
import com.sd.fm.bean.SdFileDirBean;
import com.sd.fm.common.RedisSequenceConstants;
import com.sd.fm.dao.FtpFileUploadDao;
import com.sd.fm.dao.SdFileInfoDao;

public class SdFileInfoService {
    
    private RedisManager redisManager = null;
    private FtpFileUploadDao ftpFileUploadDao = null;
    private SdFileInfoDao sdFileInfoDao = null;
    
    
    public SdFileInfoDao getSdFileInfoDao() {
        return sdFileInfoDao;
    }
    public void setSdFileInfoDao(SdFileInfoDao sdFileInfoDao) {
        this.sdFileInfoDao = sdFileInfoDao;
    }
    public RedisManager getRedisManager() {
        return redisManager;
    }
    public void setRedisManager(RedisManager redisManager) {
        this.redisManager = redisManager;
    }
    public FtpFileUploadDao getFtpFileUploadDao() {
        return ftpFileUploadDao;
    }
    public void setFtpFileUploadDao(FtpFileUploadDao ftpFileUploadDao) {
        this.ftpFileUploadDao = ftpFileUploadDao;
    }
    
    /**
     * 根据文件ID获取单个文件信息
     * @param fileId
     * @return
     */
    public SdFileBean getFileInfo(String fileId) {
        if(null != fileId && !"".equals(fileId)) {
            return ftpFileUploadDao.getFileInfoById(fileId);
        }
        return null;
    }
    
    /**
     * 创建文件夹
     * @param dirName
     * @param parentDirId
     */
    public void createDir(String userId, String dirName, String parentDirId) {
        SdFileDirBean bean = new SdFileDirBean();
        
        long dirId = redisManager.getSequence(RedisSequenceConstants.SEQ_FILE_DIR_INFO);
        bean.setDirId(dirId + "");
        bean.setDirName(dirName);
        bean.setCruserId(userId);
        bean.setCrtime(new SdTime(SdDateUtil.getNowTime()));
        bean.setStatus("1");
        //存在父节点时
        if(null != parentDirId && !"".equals(parentDirId)) {
            bean.setParentDirId(parentDirId);
            SdFileDirBean parentDir = sdFileInfoDao.getFileDirInfoById(parentDirId);
            if(parentDir.getDirPathId() == null || parentDir.getDirPathName() == null) {
                parentDir.setDirPathId("");
                parentDir.setDirPathName("");
            }
            bean.setDirPathId(parentDir.getDirPathId() + parentDirId + ",");
            bean.setDirPathName(parentDir.getDirPathName() + parentDir.getDirName() + ",");
        }else {
            bean.setParentDirId("");
            bean.setDirPathId("");
            bean.setDirPathName("");
        }
        sdFileInfoDao.createDir(bean);
    }
    
    /**
     * 根据父节点获取文件夹，带分页
     * @param parentDirId
     * @param userId
     * @return
     */
    public HashMap<String, Object> getFileDirPageList(String parentDirId, String userId, String nameSign, int pageNo, int pageSize){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        
        List<SdFileDirBean> fileDirList = sdFileInfoDao.getFileDirPageList(parentDirId, userId, nameSign, pageNo, pageSize);
        int fileDirListCount = sdFileInfoDao.getFileDirListCount(parentDirId, userId, nameSign);
        
        returnMap.put("fileDirList", fileDirList);
        returnMap.put("fileDirListCount", fileDirListCount);
        return returnMap;
    }
    
    /**
     * 根据父节点获取文件夹，不带分页
     * @param parentDirId
     * @param userId
     * @param nameSign
     * @return
     */
    public JSONArray getFileDirList(String parentDirId, String userId, String nameSign){
        List<SdFileDirBean> fileDirList = sdFileInfoDao.getFileDirList(parentDirId, userId, nameSign);
        List<HashMap<String, Object>> fileDirChildCountList = sdFileInfoDao.getFileDirChildCount(parentDirId, userId, nameSign);
        
        return createTreeData(fileDirList, fileDirChildCountList);
    }
    
    /**
     * 
     * @param fileDirList
     * @param fileDirChildCount
     * @return
     */
    private JSONArray createTreeData(List<SdFileDirBean> fileDirList, List<HashMap<String, Object>> fileDirChildCountList) {
        JSONArray fileDirArray = new JSONArray();
        //文件夹子文件夹数
        HashMap<String, Object> countMap = new HashMap<String, Object>();
        if(null != fileDirChildCountList && fileDirChildCountList.size() > 0) {
            for(HashMap<String, Object> map : fileDirChildCountList) {
                countMap.put(map.get("DIRID").toString(), map.get("COUNT").toString());
            }
        }
        //处理节点数据
        if(null != fileDirList && fileDirList.size() > 0) {
            JSONObject fileDir = null;
            for(SdFileDirBean bean : fileDirList) {
                fileDir = (JSONObject) JSONObject.toJSON(bean);
                String dirId = bean.getDirId();
                if(null != countMap.get(dirId)) {
                    fileDir.put("isParent", "true");
                }else {
                    fileDir.put("isParent", "false");
                }
                fileDirArray.add(fileDir);
            }
        }
        return fileDirArray;
    }
    

    /**
     * 获取文件夹下文件
     * @param userId
     * @param fileName
     * @param parentDirId
     * @param fileType
     * @param status
     * @param pageNo
     * @param pageSize
     * @return
     */
    public HashMap<String, Object> getFileList(String userId, String fileName, String parentDirId, String fileType, String status, int pageNo, int pageSize){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        
        List<SdFileBean> fileList = sdFileInfoDao.getFileList(userId, fileName, parentDirId, fileType, status, pageNo, pageSize);
        int totalCount = sdFileInfoDao.getFileListCount(userId, fileName, parentDirId, fileType, status);
        returnMap.put("fileList", fileList);
        returnMap.put("totalCount", totalCount);
        
        return returnMap;
    }
    
    /**
     * 根据文件夹Id删除文件夹
     * @param dirId
     * @return
     */
    public int deleteFileDirById(String dirId, String userId) {
        //0：文件夹下存在文件，1：删除成功，2：删除失败
        int type = 0;
        int fileListCount = sdFileInfoDao.getFileListCount(userId, null, dirId, null, null);
        if(fileListCount == 0) {
            int deleteCount = sdFileInfoDao.deleteFileDirById(dirId);
            if(deleteCount > 0) {
                type = 1;
            }else {
                type = 2;
            }
        }
        return type;
    }
    
    /**
     * 根据id删除文件
     * @param fileId
     * @return
     */
    public boolean deleteFileById(ArrayList<String> fileIdArray) {
        int deleteCount = 0;
        for (String fileId : fileIdArray) {
            deleteCount += sdFileInfoDao.deleteFileById(fileId);
        }
        
        if(deleteCount > 0) {
            return true;
        }
        return false;
    }
    /**
     * 根据id删除文件，物理删除
     * @param fileId
     * @return
     */
    public boolean deleteFileByIdPhysical(ArrayList<String> fileIdArray) {
        int deleteCount = 0;
        for (String fileId : fileIdArray) {
            /**
             * 文件服务器上文件的处理，可以考虑这里删除，也可以后续进行整体删除
             */
            deleteCount += sdFileInfoDao.deleteFileByIdPhysical(fileId);
        }
        
        if(deleteCount > 0) {
            return true;
        }
        return false;
    }
    
    /**
     * 根据id恢复文件
     * @param fileId
     * @return
     */
    public boolean recoverFile(ArrayList<String> fileIdArray) {
        int deleteCount = 0;
        for (String fileId : fileIdArray) {
            deleteCount += sdFileInfoDao.recoverFileById(fileId);
        }
        
        if(deleteCount > 0) {
            return true;
        }
        return false;
    }
    
    /**
     * 文件重命名
     * @param fileId
     * @param newName
     * @return
     */
    public boolean updateFileName(String fileId, String newFileName) {
        int updateCount = sdFileInfoDao.updateFileName(fileId, newFileName);
        if(updateCount > 0) {
            return true;
        }
        return false;
    }
    
    /**
     * 文件夹重命名
     * @param fileId
     * @param newName
     * @return
     */
    public boolean updateFileDirName(String dirId, String dirName) {
        int updateCount = sdFileInfoDao.updateFileDirName(dirId, dirName);
        if(updateCount > 0) {
            return true;
        }
        return false;
    }
    
    
    
    
}

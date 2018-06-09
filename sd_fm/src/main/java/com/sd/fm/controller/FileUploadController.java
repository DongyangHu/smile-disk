package com.sd.fm.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sd.common.random.UUIDProvider;
import com.sd.fm.service.FtpFileUploadService;
import com.sd.sso.filter.SdSessionConstant;
import com.sd.sso.filter.bean.SdUserSessionInfoBean;

@Controller
@RequestMapping("fileUpload")
public class FileUploadController {
    
    public static Log logger = LogFactory.getLog(FileUploadController.class);
    
    @Autowired(required=true)
    private FtpFileUploadService ftpFileUploadService = null;
    
    @RequestMapping(value="upload", method=RequestMethod.POST, produces="text/plain;charset=UTF-8")
    public @ResponseBody String uploadFile(MultipartHttpServletRequest request, HttpServletResponse response) {
        SdUserSessionInfoBean userInfo = (SdUserSessionInfoBean) request.getSession().getAttribute(SdSessionConstant.SESSION_USER_INFO);
        String dirId = request.getParameter("dirId");
        if(null == dirId || "".equals(dirId)) {
            dirId = "";
        }
        String userCode = userInfo.getUserCode();
        String userId = userInfo.getUserId();
        MultipartFile file = request.getFile("file");
        String fileName = file.getOriginalFilename();
        //文件后缀名
        String extension = FilenameUtils.getExtension(fileName);
        //保存到系统的文件名
        String saveFileName = UUIDProvider.createUUID() + "." + extension;
        //获取文件大小
        double size = file.getSize();
        String fileSize = (size/1024/1024) + "";
        fileSize = fileSize.substring(0, fileSize.indexOf(".") + 3) + "Mb";
        try {
            //fileUploadService.saveFile(file.getInputStream(),file.getSize(), "hudy", saveFileName);
            ftpFileUploadService.uploadFile(userCode, userId, file.getInputStream(), dirId, fileName, saveFileName, fileSize);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }
    
    /**
     * 文件下载
     * @param fileId
     * @param request
     * @param response
     */
    @RequestMapping(value="download", method=RequestMethod.GET, produces="text/plain;charset=UTF-8")
    public void downloadFile(@RequestParam String fileId,HttpServletRequest request, HttpServletResponse response) {
        try {
            HashMap<String, Object> downloadFile = ftpFileUploadService.downloadFile(fileId);
            InputStream inputStream = (InputStream) downloadFile.get("in");
            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            String fileName = request.getParameter("fileName");
            //未传递文件名称时
            if(null == fileName || "".equals(fileName)) {
                String fileLable = (String) downloadFile.get("fileName");
                fileLable = new String(fileLable.getBytes(),"ISO-8859-1");
                response.setHeader("Content-Disposition", "attachment;fileName=" + fileLable);
            }else {
                //转码
                fileName = URLDecoder.decode(URLDecoder.decode(fileName, "UTF-8"), "UTF-8");
                fileName = new String(fileName.getBytes(), "ISO-8859-1");
                response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
            }
            
            OutputStream os = response.getOutputStream();
            byte[] b = new byte[8192];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
            }
            os.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

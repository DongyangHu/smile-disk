package com.sd.common.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

/**
 * @see ftp文件上传下载工具类
 * @version 0.0.1
 * @author HuDongyang
 * @blog http://blog.csdn.net/hu_dongyang
 * @date 2018.02.06
 */
public class FtpUtil {
    
    private static Log logger = LogFactory.getLog(FtpUtil.class);
    
    /**
     * 获取ftp连接
     * @param host 主机
     * @param port 端口号
     * @param user ftp用户
     * @param password ftp用户密码
     * @return FTPClient
     * @throws SocketException
     * @throws IOException
     */
    private static FTPClient getFtpClient(String host, String port, String user, String password) throws SocketException, IOException{
        int ftpPort = Integer.valueOf(port);
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(host, ftpPort);
        ftpClient.login(user, password);
        int replyCode = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(replyCode)) {
            ftpClient.disconnect();
            logger.error("connection denied!");
            return null;
        }
        return ftpClient;
    }
    
    /**
     * FTP文件上传
     * @param host ftp主机
     * @param port ftp端口
     * @param user ftp用户
     * @param password ftp用户密码
     * @param fileIn 文件输入流
     * @param basePath 文件上传基础路径
     * @param filePath 文件上传子目录
     * @param fileName 存储的文件名
     * @return
     * @throws SocketException
     * @throws IOException
     */
    public static boolean ftpUpload(String host, String port, String user, String password, InputStream fileIn,String basePath, String filePath, String fileName) throws SocketException, IOException {
        FTPClient ftpClient = getFtpClient(host, port, user, password);
        //设置上传目录  
        
        ftpClient.makeDirectory(basePath);
        ftpClient.changeWorkingDirectory(basePath);
        //创建文件保存目录
        String[] dirArray = filePath.split("/");
        for(int i = 0; i < dirArray.length; i++) {
            ftpClient.makeDirectory(dirArray[i]);
            ftpClient.changeWorkingDirectory(dirArray[i]);
        }
        ftpClient.setBufferSize(1024);
        ftpClient.setControlEncoding("GBK");  
        //设置文件类型（二进制）  
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);  
        boolean storeFile = ftpClient.storeFile(fileName, fileIn);
        fileIn.close();
        ftpClient.disconnect();
        return storeFile;
    }
    
    /**
     * 获取文件输入流
     * @param host
     * @param port
     * @param user
     * @param password
     * @param basePath
     * @param filePath
     * @param fileName
     * @return
     * @throws SocketException
     * @throws IOException
     */
    public static InputStream ftpDownload(String host, String port, String user, String password, String basePath, String filePath, String fileName) throws SocketException, IOException {
        InputStream in = null;
        FTPClient ftpClient = getFtpClient(host, port, user, password);
        //切换到路径下
        ftpClient.changeWorkingDirectory(basePath);
        ftpClient.changeWorkingDirectory(filePath);
        ftpClient.setBufferSize(1024);
        ftpClient.setControlEncoding("GBK");
        //设置文件类型（二进制）  
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        FTPFile[] files = ftpClient.listFiles();
        for(int i = 0; i < files.length; i++) {
            if(fileName.equals(files[i].getName())) {
                in = ftpClient.retrieveFileStream(fileName);
            }
        }
        return in;
    }
    
    /**
     * 删除文件
     * @param host ftp主机
     * @param port ftp端口
     * @param user ftp用户
     * @param password ftp用户密码
     * @param fileIn 文件输入流
     * @param basePath 文件上传基础路径
     * @param filePath 文件上传子目录
     * @param fileName 存储的文件名
     * @return
     * @throws SocketException
     * @throws IOException
     */
    public static boolean ftpDeleteFile(String host, String port, String user, String password,String basePath, String filePath, String fileName) throws SocketException, IOException {
        FTPClient ftpClient = getFtpClient(host, port, user, password);
        //文件路径
        String fileAddr = basePath + "/" + filePath + "/" + fileName;
        //删除文件
        boolean deleteFile = ftpClient.deleteFile(fileAddr);
        ftpClient.disconnect();
        return deleteFile;
    }
    
/*    public static void main(String[] args) throws SocketException, IOException {
        File file = new File("c:/20180108125217521710.6002531246384414.jpg");
        InputStream inputStream = new FileInputStream(file);
        boolean ftpUpload = ftpUpload("127.0.0.1", "21", "hudy", "123456", inputStream, null, "/luocong/test/2", "123.jpg");
        System.out.println(ftpUpload);
        InputStream in = ftpDownload("127.0.0.1", "21", "hudy", "123456", "userFiles", "hudy\\2018\\2\\3", "76cceb5ee45f48ad922350f5a460f78f.jpg");
        
        File file = new File("c:\\test.jpg");
        OutputStream outputStream = new FileOutputStream(file);
        byte[] buff = new byte[8192];
        int readSize = 0;
        while((readSize = in.read(buff, 0, 8192)) != -1) {
            outputStream.write(buff, 0, readSize);
        }
        outputStream.close();
        in.close();
    }*/
}

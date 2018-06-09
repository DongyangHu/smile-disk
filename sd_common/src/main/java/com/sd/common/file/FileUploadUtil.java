package com.sd.common.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author HuDongyang
 * @version 0.0.1
 * @date 2018.01.23
 */
public class FileUploadUtil {
    
    /**
     * 
     * 输入流保存文件
     * @param fileInputStream 文件输入流
     * @param fileName 保存的文件名
     * @param filePath 保存的路径
     * @return 文件传输的字节数
     * @throws IOException
     */
    public static long saveFile(InputStream fileInputStream, String fileName, String filePath) throws IOException {
        long fileSize = 0;
        //创建文件
        File file = new File(filePath + File.separator + fileName);
        //判断文件夹是否存在
        if(!file.getParentFile().isDirectory()) {
            file.getParentFile().mkdirs();
        }
        //输出流
        OutputStream outputStream = new FileOutputStream(file);
        //文件写入的字节数
        int writterSize = 0;
        byte[] buff = new byte[8192];
        while((writterSize = fileInputStream.read(buff, 0, 8192)) != -1 ) {
            outputStream.write(buff, 0, writterSize);
            fileSize += writterSize;
        }
        outputStream.flush();
        outputStream.close();
        fileInputStream.close();
        return fileSize;
    }
}

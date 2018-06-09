package com.sd.baseData.util;

import com.sd.common.mail.SendMailUtil;

public class MailTest {
    public static void main(String[] args) throws Exception{
        
        
        
        SendMailUtil.SendMailDebug("632433151@qq.com", "hudy", "<html lang='zh-CN'><head ><meta charset='utf-8'>"
                + "</head><body>内容：感谢注册Smile Disk,请点击链接激活"
                + "<a href='http://yiyang66.top:8099'>【Smile Disk】</a></body></html>","主题");
    }
}

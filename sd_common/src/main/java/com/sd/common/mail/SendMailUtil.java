package com.sd.common.mail;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sd.common.encryp.EncrypConstants;
import com.sd.common.encryp.EncryptAES;
import com.sun.mail.util.MailSSLSocketFactory;

public class SendMailUtil {

    
    private static Log logger = LogFactory.getLog(SendMailUtil.class);
    private static String fromMail = null;
    private static String mailPwd = null;
    private static String smtpHost = null;
    private static int smtpPort = 0;
    private static String protocol = null;
    
    /**
     * 初始化连接参数
     */
    static {
        InputStream inStream = SendMailUtil.class.getClassLoader().getResourceAsStream("sdMail.properties");
        Properties props = new Properties();
        try {
            if(inStream != null) {
                props.load(inStream);
                fromMail = props.getProperty("fromMail", null);
                mailPwd = EncryptAES.decryptAES(props.getProperty("mailPwd", null), EncrypConstants.AES_DECRYPT_KEY);
                smtpHost = props.getProperty("smtpHost", null);
                smtpPort = Integer.valueOf(props.getProperty("smtpPort", null));
                protocol = props.getProperty("protocol", null);
            }
            else {
                logger.warn("sdMail.properties文件不存在");
            }
        } catch (IOException e) {
            logger.warn("读取sdMail.properties文件异常",e);
        }
    }
    
    /**
     * 发送邮件
     */
    public static void SendMailDebug(String receiveMail, String userCode, String mailContent, String mailSubject) throws Exception{
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", protocol);
        //props.setProperty("mail.smtp.host", smtpHost);
        //props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.auth", "true");
        
        Session session = Session.getInstance(props);
        session.setDebug(true);
        //创建邮件
        MimeMessage message = createMessage(session, receiveMail, userCode, mailContent, mailSubject);
        //获取邮件传输对象
        Transport transport = session.getTransport();
        transport.connect(smtpHost, smtpPort, fromMail, mailPwd);
        //发送邮件
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
        
    }
    /**
     * 发送邮件
     */
    public static void SendMail(String receiveMail, String userCode, String mailContent, String mailSubject) throws Exception{
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", protocol);
        props.setProperty("mail.smtp.host", smtpHost);   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");
        
        Session session = Session.getInstance(props);
        session.setDebug(false);
        //创建邮件
        MimeMessage message = createMessage(session, receiveMail, userCode, mailContent, mailSubject);
        //获取邮件传输对象
        Transport transport = session.getTransport();
        transport.connect(smtpHost, smtpPort, fromMail, mailPwd);
        //发送邮件
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
        
    }
    
    /**
     * 创建邮件
     * @param session 
     * @param receiveMail 收件人邮箱
     * @param userCode 收件人用户名
     * @param mailContent 邮件正文 
     * @return
     * @throws Exception
     */
    private static MimeMessage createMessage (Session session, String receiveMail, String userCode, String mailContent, String mailSubject) throws Exception{
        
        //发件人
        Address addrSend = new InternetAddress(fromMail, "SmileDisk", "UTF-8");
        //收件人
        Address addrGet = new InternetAddress(receiveMail, userCode, "UTF-8");
        //创建一封邮件
        MimeMessage message = new MimeMessage(session);
        //发件人
        message.setFrom(addrSend);
        //收件人
        message.setRecipient(MimeMessage.RecipientType.TO, addrGet);
        //邮件主题
        message.setSubject(mailSubject, "UTF-8");
        //邮件正文
        message.setContent(mailContent, "text/html;charset=UTF-8");
        //发送时间
        message.setSentDate(new Date());
        //保存前面的设置
        message.saveChanges();
        
        return message;
    }
    
    /**
     * 发送邮件SSL
     */
    public static void SendMailSSLDebug(String receiveMail, String userCode, String mailContent, String mailSubject) throws Exception{
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", protocol);
        //props.setProperty("mail.smtp.host", smtpHost);
        //props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.auth", "true");
        
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.socketFactory", sf);
        
        Session session = Session.getInstance(props);
        session.setDebug(true);
        //创建邮件
        MimeMessage message = createMessage(session, receiveMail, userCode, mailContent, mailSubject);
        //获取邮件传输对象
        Transport transport = session.getTransport();
        transport.connect(smtpHost, smtpPort, fromMail, mailPwd);
        //发送邮件
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }
    
    /**
     * 发送邮件SSL
     */
    public static void SendMailSSL(String receiveMail, String userCode, String mailContent, String mailSubject) throws Exception{
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", protocol);
        //props.setProperty("mail.smtp.host", smtpHost);
        //props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.auth", "true");
        
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.socketFactory", sf);
        
        Session session = Session.getInstance(props);
        session.setDebug(false);
        //创建邮件
        MimeMessage message = createMessage(session, receiveMail, userCode, mailContent, mailSubject);
        //获取邮件传输对象
        Transport transport = session.getTransport();
        transport.connect(smtpHost, smtpPort, fromMail, mailPwd);
        //发送邮件
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }
}

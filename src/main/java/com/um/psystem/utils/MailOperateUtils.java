package com.um.psystem.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * @Author: zhenjin.zheng
 * @Description:
 * @Date: 2020/6/3
 */
//邮箱操作
@Component
public class MailOperateUtils {

    private static String MAIL_HOST ;

    private static String STABLE_SENDER ;

    private static String SENDER_PWD ;

    public static String getMailHost() {
        return MAIL_HOST;
    }

    @Value("${mail.host}")
    public void setMailHost(String mailHost) {
        MailOperateUtils.MAIL_HOST = mailHost;
    }

    public static String getStableSender() {
        return STABLE_SENDER;
    }

    @Value("${stable.sender}")
    public void setStableSender(String stablesender) {
        MailOperateUtils.STABLE_SENDER = stablesender;
    }

    public static String getSenderPwd() {
        return SENDER_PWD;
    }
    @Value("${sender.pwd}")
    public void setSenderPwd(String senderPwd) {
        MailOperateUtils.SENDER_PWD = senderPwd;
    }

    public static void  sendMail(String msg,String title ,String mailToAddress) throws MessagingException, UnsupportedEncodingException {
        //登录邮箱
        Properties mailProperties = new Properties();
        //设置发件人的SMTP服务器地址
        mailProperties.setProperty("mail.smtp.host",MAIL_HOST);
        //设置用户的认证方式
        mailProperties.setProperty("mail.smtp.auth", "true");
        //设置传输协议
        mailProperties.setProperty("mail.transport.protocol", "smtp");
        Session mailSession =  Session.getInstance(mailProperties);
        mailSession.setDebug(true);
        Transport mailTransport = mailSession.getTransport();
        //连接固定邮箱
        mailTransport.connect(MAIL_HOST,STABLE_SENDER,SENDER_PWD);
        Message mailMessage = createMailTextMessage(mailSession,title,mailToAddress,msg);
        mailTransport.sendMessage(mailMessage,mailMessage.getAllRecipients());
        mailTransport.close();
    }

    /**
     * 创建简单的文本邮件
     * @param mailSession
     * @param mailTitle
     * @param mailTo
     * @param textContent
     * @return
     * @throws MessagingException
     */
    public static Message  createMailTextMessage(Session mailSession,String mailTitle,String mailTo,String textContent) throws MessagingException, UnsupportedEncodingException {
        Message mailMessage = new MimeMessage(mailSession);
        mailMessage.setFrom(new InternetAddress(STABLE_SENDER,"九联工程","UTF-8"));
        // 指明邮件的收件人，
        mailMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));

        // 邮件的标题
        mailMessage.setSubject(mailTitle);
        // 邮件的文本内容
        mailMessage.setContent(textContent, "text/html;charset=UTF-8");
        // 返回创建好的邮件对象
        return mailMessage;

    }

    public static void main(String[]args){
        try {
            sendMail("邮件发送测试","测试测试","zhenjin.zheng@unionman.com.cn");
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}

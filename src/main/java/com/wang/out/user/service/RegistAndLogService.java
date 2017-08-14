package com.wang.out.user.service;

import com.wang.out.common.ResultPojo;
import com.wang.out.user.dao.RegistAndLogDao;
import org.apache.commons.net.smtp.SMTPClient;
import org.apache.commons.net.smtp.SMTPReply;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.*;

import java.io.IOException;

import org.xbill.DNS.Lookup;
import org.xbill.DNS.Record;
import org.xbill.DNS.Type;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


@Service("registAndLogService")
public class RegistAndLogService {

    @Resource
    RegistAndLogDao registAndLogDao;

    /*
    * 验证用户名和密码还有注册状态
    * */
    public ResultPojo checklogService(String username, String password) throws SQLException {

        Map<String, String> map = registAndLogDao.checklogDao(username, password);
        if (map.get("username").equals("")) {
            return new ResultPojo(110, "用户名或密码错误");
        } else {
            if (map.get("isregister").equals("1")) {
                return new ResultPojo(200, "验证成功");
            } else {
                return new ResultPojo(111, "账户未激活");
            }
        }

    }


    /*
    * 登录生成token
    * */
    public String createTokenService(String username) throws SQLException {
        String token = UUID.randomUUID().toString();
        Long time = System.currentTimeMillis() + 3 * 60 * 60 * 1000;
        registAndLogDao.createTokenDao(username, token, time);
        return token;
    }

    /*
    * 更改注册状态
    * */

    public boolean checkedService(String checked) throws SQLException {

        return registAndLogDao.checkedDao(checked);
    }

    /*
    * 新用户注册
    * */
    public void userRegistService(Map<String, String[]> usermap) throws SQLException, MessagingException {

            /*
            * 添加用户ID和未激活状态
            * */
        usermap.put("id", new String[]{UUID.randomUUID().toString()});
        usermap.put("isregister", new String[]{"0"});

        Map<String, String[]> checkmap = new HashMap<String, String[]>();
        checkmap.put("username", usermap.get("username"));
        checkmap.put("checked", new String[]{UUID.randomUUID().toString()});
        registAndLogDao.userRegistDao(usermap, checkmap);
//            发送邮件
//            this.sendEmail(usermap.get("username")[0],checkmap.get("checked")[0]);

    }

    /*
    *
    * 发送Email
    * */

    public void sendEmail(String email, String checkedid) throws SQLException, MessagingException {

        //创建一封邮件
        //用于连接邮件服务器的参数配置（发送邮件时才需要用到）
        Properties properties = new Properties();
        // 创建信件服务器
        properties.put("mail.smtp.host", "smtp.qq.com");//主机host，跟邮件发送者必须一致
        properties.put("mail.smtp.auth", "true"); // 通过验证，也就是用户名和密码的验证，必须要有这一条　
        properties.put("mail.smtp.port", 465);//加密服务端口465
//            properties.put("mail.smtp.ssl.enable", true);

        // 发送邮件协议名称
        properties.setProperty("mail.transport.protocol", "smtp");

        properties.put("mail.smtp.ssl.enable", "true");//加密
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                //登陆邮箱，密码
                return new PasswordAuthentication("308291390@qq.com", "gnrxctgkjjbjbhjg");
            }
        });
//            Session session = Session.getInstance(properties);
        //根据参数配置，创建回话对象（为了发送邮件准备的）
        //Session session = Session.getDefaultInstance(properties);
        //debug
        session.setDebug(true);
        //创建邮件对象
        MimeMessage message = new MimeMessage(session);
        //邮件几个必须的：发件人，收件人，邮件主题，邮件内容

        //1、from :发件人
        //        其中 InternetAddress 的三个参数分别为: 邮箱, 显示的昵称(只用于显示, 没有特别的要求), 昵称的字符集编码
        //    真正要发送时, 邮箱必须是真实有效的邮箱。
        message.setFrom(new InternetAddress("308291390@qq.com"));
        //2、TO :收件人
        //MimeMessage.RecipientType.TO  直接发送人
        //MimeMessage.RecipientType.CC  抄送人（可选）
        //MimeMessage.RecipientType.BCC  秘密发送人（可选）
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress("308291390@qq.com"));
        message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(email));
        //3、Suject :邮件主题
        message.setSubject("pic身份验证", "UTF-8");
        //邮件内容
        ///邮件的内容
        //4、Content :邮件正文（可以使用html标签）
        message.setContent("你好：" + email + "成功注册pic，请点击下面链接激活账号,http://localhost:8089/hello/check?checkid=" + checkedid, "text/html;charset=UTF-8");
        //5、设置显示的发件时间
        message.setSentDate(new Date());
        //6、保存前面设置的
        message.saveChanges();
        //7、发送
        Transport.send(message);
    }

    /*
    * 判断是否是Email
    * */

    public static boolean checkEmail(String email) {
        if (!email.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+")) {
            return false;
        }
        String host = "";
        String hostName = email.split("@")[1];
        Record[] result = null;
        SMTPClient client = new SMTPClient();
        try {
            // 查找MX记录
            Lookup lookup = new Lookup(hostName, Type.MX);
            lookup.run();
            if (lookup.getResult() != Lookup.SUCCESSFUL) {
                return false;
            } else {
                result = lookup.getAnswers();
            }
            // 连接到邮箱服务器
            for (int i = 0; i < result.length; i++) {
                host = result[i].getAdditionalName().toString();
                client.connect(host);
                if (!SMTPReply.isPositiveCompletion(client.getReplyCode())) {
                    client.disconnect();
                    continue;
                } else {
                    break;
                }
            }
            //以下2项自己填写快速的，有效的邮箱
            client.login("163.com");
            client.setSender("sxgkwei@163.com");
            client.addRecipient(email);
            if (250 == client.getReplyCode()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                client.disconnect();
            } catch (IOException e) {
            }
        }
        return false;
    }


    /*
    * 密码格式：字母和数字构成，不能超过16位；
    * */

    public static boolean isValid(String password) {
        if (password.length() < 3 || password.length() > 33) {
            return false;
        } else {
            int numberCounter = 0;
            for (int i = 0; i < password.length(); i++) {
                char c = password.charAt(i);
                if (!Character.isLetterOrDigit(c)) {
                    return false;
                }
                if (Character.isDigit(c)) {
                    numberCounter++;
                }
            }
            if (numberCounter < 2) {
                return false;
            }
        }
        return true;
    }



    /*
    * 判断用户名是否存在
    * */

    public boolean usernameIsExistService(String username) throws SQLException {

        return registAndLogDao.usernameIsExistDao(username);
    }

}

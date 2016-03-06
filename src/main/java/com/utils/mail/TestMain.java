package com.utils.mail;

import javax.mail.internet.MimeBodyPart;


public class TestMain {

	public static void main(String[] args) throws Exception{   
        //这个类主要是设置邮件   
     MailSenderInfo mailInfo = new MailSenderInfo();    
     mailInfo.setMailServerHost("smtp.exmail.qq.com");    
     mailInfo.setMailServerPort("25");    
     mailInfo.setValidate(true);    
     mailInfo.setUserName("yangshukai@haistand.com.cn");    
     mailInfo.setPassword("mbtj2015ysk+");//您的邮箱密码    
     mailInfo.setFromAddress("yangshukai@haistand.com.cn");    
     mailInfo.setToAddress("735181886@qq.com");    
     mailInfo.setSubject("This is a test mail");    
     mailInfo.setContent("For test, ignore");
     MimeBodyPart attachment=SentEmailUtils.createAttachment("D:/JAVA/New_Workspace/Company/WebRoot/file/1316777324447793152.xlsx");
     mailInfo.setAttachment(attachment);
     SimpleMailSender sms = new SimpleMailSender();   
//   sms.sendTextMail(mailInfo);//发�?�文体格�?    
     sms.sendHtmlMail(mailInfo);//发�?�html格式   
   }  


}

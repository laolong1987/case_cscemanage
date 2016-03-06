package com.utils.mail;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.internet.MimeBodyPart;

public class SentEmailUtils {
    /**  
     * 根据传入的文件路径创建附件并返回  
     */ 
    public static MimeBodyPart createAttachment(String fileName) throws Exception {  
        MimeBodyPart attachmentPart = new MimeBodyPart();  
        FileDataSource fds = new FileDataSource(fileName);  
        attachmentPart.setDataHandler(new DataHandler(fds));  
        attachmentPart.setFileName(fds.getName());  
        return attachmentPart;  
    }
    
	public static void sentEmail(String fromAddress,String password,String toAddress,String filePath) throws Exception{
		 MailSenderInfo mailInfo = new MailSenderInfo();    
	     mailInfo.setMailServerHost("smtp.qq.com");    
	     mailInfo.setMailServerPort("25");    
	     mailInfo.setValidate(true);    
	     mailInfo.setUserName(fromAddress);    
	     mailInfo.setPassword(password);//您的邮箱密码    
	     mailInfo.setFromAddress(fromAddress);    
	     mailInfo.setToAddress(toAddress);    
	     mailInfo.setSubject("安悦e贸通产品简介");    
	     mailInfo.setContent("<p>尊敬的用户您好：</p><p>欢迎您关注安悦e贸通。请到邮件附件中查看您关注的产品信息。"
	     		+ "更多产品和服务，请登录安悦e贸通电商平台网站：www.anyomall.com</p><p>安悦e贸通市场部</p>"
	     		+ "<p>上海市嘉定区博乐南路125号</p><p>热线电话：4008 215 292</p>"
	     		+ "<p>传真：021-59161125</p>");
	     MimeBodyPart attachment=createAttachment(filePath);
	     mailInfo.setAttachment(attachment);
	     SimpleMailSender sms = new SimpleMailSender();   
	//   sms.sendTextMail(mailInfo);//发送文体格式    
	     sms.sendHtmlMail(mailInfo); //发送html格式   
	}  
	
	public static void sentEmailNullFile(String fromAddress,String password,String toAddress) throws Exception{
		 MailSenderInfo mailInfo = new MailSenderInfo();    
	     mailInfo.setMailServerHost("smtp.qq.com");    
	     mailInfo.setMailServerPort("25");    
	     mailInfo.setValidate(true);    
	     mailInfo.setUserName(fromAddress);    
	     mailInfo.setPassword(password);//您的邮箱密码    
	     mailInfo.setFromAddress(fromAddress);    
	     mailInfo.setToAddress(toAddress);    
	     mailInfo.setSubject("安悦e贸通产品简介");    
	     mailInfo.setContent("<p>尊敬的用户您好：</p><p>欢迎您关注安悦e贸通。请到邮件附件中查看您关注的产品信息。"
	     		+ "更多产品和服务，请登录安悦e贸通电商平台网站：www.anyomall.com</p><p>安悦e贸通市场部</p>"
	     		+ "<p>上海市嘉定区博乐南路125号</p><p>热线电话：4008 215 292</p>"
	     		+ "<p>传真：021-59161125</p>");
	     SimpleMailSender sms = new SimpleMailSender();   
	//   sms.sendTextMail(mailInfo);//发送文体格式    
	     sms.sendMailHtml(mailInfo); //发送html格式   ,无文件
	}  
	
	public static void main(String[] args) throws Exception{   
        //这个类主要是设置邮件   
		sentEmail("735181886@qq.com","mbtj2015ysk+","yangshukai@haistand.com.cn","E:\\test.txt");
		
   }  
}

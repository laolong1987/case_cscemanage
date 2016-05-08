package com.utils.mail;

import java.text.SimpleDateFormat;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.internet.MimeBodyPart;

import com.web.entity.CaseManage;

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

    public static void sentEmail(String fromAddress, String password, String toAddress, String filePath) throws Exception {
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
        MimeBodyPart attachment = createAttachment(filePath);
        mailInfo.setAttachment(attachment);
        SimpleMailSender sms = new SimpleMailSender();
        //   sms.sendTextMail(mailInfo);//发送文体格式
        sms.sendHtmlMail(mailInfo); //发送html格式
    }

    public static void sentEmailNullFile(String toAddress, String username, CaseManage caseManage) throws Exception {
        MailSenderInfo mailInfo = new MailSenderInfo();
        mailInfo.setMailServerHost("smtp.hostuc.net");
        mailInfo.setMailServerPort("25");
        mailInfo.setValidate(true);
        mailInfo.setUserName("info@advance-medical.com.cn");
        mailInfo.setPassword("d98420");//您的邮箱密码
        mailInfo.setFromAddress("info@advance-medical.com.cn");
        mailInfo.setToAddress(toAddress);
        mailInfo.setSubject("Advance Medical Info");
        StringBuffer content = new StringBuffer();
        content.append("<div style='font-family:arial'>");
        content.append("<p >Dear " + username + ":</p>");
        content.append("<p >We have received an online enrollment form from one of ");
        content.append(" our Chinese client employees through our patient portal.");
        content.append("   Could you please look at the following and confirm within ");
        content.append("the next 2-3 hours whether or not you can take on this case today?  Thank you.").append("</p><p></p>");
        content.append("服务类型(service type)：");
        if (caseManage.getType() == 1) {
            content.append("Expert Medical Report");
        } else if (caseManage.getType() == 2) {
            content.append("Personal Healthy Advisory");
        } else if (caseManage.getType() == 3) {
            content.append("Stress Management");
        } else {
            content.append(" Oientation And Navigation");
        }
        content.append("<br>");
        content.append("case创建时间(time created):").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(caseManage.getCreatetime()));
        content.append("<br>");
        content.append("患者姓名(patient name)：").append(caseManage.getName());
        content.append("<br>");
        content.append("性别(gender)：");
        if (caseManage.getSex() == 0) {
            content.append("男");
        } else {
            content.append("女");
        }
        content.append("<br>");
        content.append("申请人与患者关系(relationship to patient)：").append(caseManage.getRelation());
        content.append("<br>");
        content.append("申请人姓名(applier name)：").append(caseManage.getCreatename());
        content.append("<br>");
        content.append("国家(country)：").append(caseManage.getCountry());
        content.append("<br>");
        content.append("省(province)：").append(caseManage.getProvince());
        content.append("<br>");
        content.append("城市(city)：").append(caseManage.getCity());
        content.append("<br>");
        content.append("详细地址(detailed address)：").append(caseManage.getAddress());
        content.append("<br>");
        content.append("首选电话(phone)：").append(caseManage.getPhone1());
        content.append("<br>");
        content.append("备选电话(secondary phone)：").append(caseManage.getPhone2());
        content.append("<br>");
        content.append("邮箱(email)：").append(caseManage.getEmail());
        content.append("<br>");
        content.append("联系时间(preferred contact time)：").append(caseManage.getPhonetime());
        content.append("<br>");
        content.append("患者情况和需求(patient description and request)：<br><div style='margin-left:20px'>").append(caseManage.getRemark()).append("</div>");
        content.append("<br>");
        content.append("医生姓名(current physician)：").append(caseManage.getDoctor_name());
        content.append("<br>");
        content.append("医生所在医院(hospital)：").append(caseManage.getDoctor_hospital());
        content.append("<br>");
        content.append("主治(specialty)：").append(caseManage.getDoctor_major());
        content.append("<br>");
        content.append("<br>");
        content.append("Best Regards" );
        content.append("<br>");
        content.append("Timothy Foggin, M.D.");
        content.append("<br>");
        content.append("Medical Director (Asia Pacific) | 国际医疗总监 advance | medical");
        content.append("</div>");
        mailInfo.setContent(content.toString());
        SimpleMailSender sms = new SimpleMailSender();
        //   sms.sendTextMail(mailInfo);//发送文体格式
        sms.sendMailHtml(mailInfo); //发送html格式   ,无文件
    }

    public static void main(String[] args) throws Exception {
        //这个类主要是设置邮件   
//        SentEmailUtils.sentEmailNullFile("735181886@qq.com", "sukeyYang");

    }
}

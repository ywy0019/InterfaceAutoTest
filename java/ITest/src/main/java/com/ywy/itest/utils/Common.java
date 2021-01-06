package com.ywy.itest.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.json.JSONObject;

import com.sun.mail.util.MailSSLSocketFactory;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Common {
	private static OkHttpClient client = new OkHttpClient();
	private static String url = "https://iserver.supermap.io/iserver/services/";
	private static String sendmail = "3284478780@qq.com";
	private static String sendmail_psd = "nalghmmcdbgbchhi";
	private static String resivmail = "503047117@qq.com";

	public static String get(String req_url) {
		Request req = new Request.Builder().url(url + req_url).get().addHeader("Cache-Control", "no-cache").build();
		String result = null;
		try {
			Response res = client.newCall(req).execute();
			if (res.toString().contains("200")){
				result = res.body().string();
			}else{
				result = null;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return result;
		}
		return result;
	}

	public static JSONObject jsonObj(String resString) {
		JSONObject jsonobj = new JSONObject(resString);
		return jsonobj;
	}

	public static String getNum(String data) {
		String num = "";
		Pattern p = Pattern.compile("\"top\":([+-]*\\d++\\.*(\\d++)*)");
		Matcher m = p.matcher(data);
		if (m.find()) {
			num = m.group(1);
		}
		return num;
	}

	public static void sendMail(String content)
			throws UnsupportedEncodingException, MessagingException, GeneralSecurityException {
		Properties properties = new Properties();
		properties.setProperty("mail.host", "smtp.qq.com");
		properties.setProperty("mail.transport.protocol", "smtp");
		properties.setProperty("mail.smtp.auth", "true");
		MailSSLSocketFactory sf = new MailSSLSocketFactory();
		sf.setTrustAllHosts(true);
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.ssl.socketFactory", sf);
		// 创建一个session对象
		Session session = Session.getDefaultInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(sendmail, sendmail_psd);
			}
		});
		// 开启debug模式
		session.setDebug(true);
		// 获取连接对象
		Transport transport = session.getTransport();
		// 连接服务器
		transport.connect("smtp.qq.com", sendmail, sendmail_psd);
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(sendmail, "USER_AA", "UTF-8"));
		message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(resivmail, "USER_AB", "UTF-8"));
		message.setSubject("iserver.supermap.io Service Test", "UTF-8");
		message.setContent(content, "text/html;charset=UTF-8");
		message.setSentDate(new Date());
		message.saveChanges();
		// 发送邮件
		transport.sendMessage(message, message.getAllRecipients());
		// 关闭连接
		transport.close();
	}
}

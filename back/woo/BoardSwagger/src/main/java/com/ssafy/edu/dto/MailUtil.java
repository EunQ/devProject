package com.ssafy.edu.dto;

import java.util.Random;

import org.apache.commons.mail.HtmlEmail;

public class MailUtil {

	public static void sendMail(String email, String subject, String msg) throws Exception {
		// Mail Server 설정
		String chatSet = "utf-8";
		String hostSMTP = "smtp.naver.com"; // SMTP 서버명
		String hostSMTPid = "dhsmf0705";
		String hostSMTPpwd = "abhu5612";

		// 보내는 사람
		String fromEmail = "dhsmf0705@naver.com";
		String fromName = "DEV";

		// email 전송
		try {
			HtmlEmail mail = new HtmlEmail();
			mail.setDebug(true);
			mail.setCharset(chatSet);
			mail.setSSLOnConnect(true);
			mail.setHostName(hostSMTP);
			mail.setSmtpPort(587);
			mail.setAuthentication(hostSMTPid, hostSMTPpwd);
			mail.setStartTLSEnabled(true);
			mail.addTo(email);
			mail.setFrom(fromEmail, fromName, chatSet);
			mail.setSubject(subject);
			mail.setHtmlMsg(msg);
			mail.send();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String createKey() throws Exception {
		StringBuffer key = new StringBuffer();
		Random rnd = new Random();

		for (int i = 0; i < 10; i++) {
			int index = rnd.nextInt(3);

			switch (index) {
			case 0:
				key.append((char) ((int) (rnd.nextInt(26)) + 97));
				break;
			case 1:
				key.append((char) ((int) (rnd.nextInt(26)) + 65));
				break;
			case 2:
				key.append((rnd.nextInt(10)));
				break;
			}
		}

		return key.toString();
	}
}

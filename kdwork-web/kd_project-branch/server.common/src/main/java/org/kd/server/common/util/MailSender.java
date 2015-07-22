package org.kd.server.common.util;

import java.io.IOException;
import java.util.Map;
import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class MailSender {
	@Inject
	private JavaMailSender javaMailSender;
	@Inject
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Inject
	private TaskExecutor taskExecutor;

	/**
	 * 构建邮件内容，发送邮件。
	 * 
	 * @param user
	 *            用户
	 * @param url
	 *            链接
	 */
	public void send(Map<String, Object> map, String title, String ftlName,
			String toEmail) {
		String text = "";

		try {
			// 从FreeMarker模板生成邮件内容
			Template template = freeMarkerConfigurer.getConfiguration()
					.getTemplate(ftlName);
			// 模板中用${XXX}站位，map中key为XXX的value会替换占位符内容。
			text = FreeMarkerTemplateUtils.processTemplateIntoString(template,
					map);
		} catch (IOException e) {
			LogRecord.error("IOException: " + e.toString());
		} catch (TemplateException e) {
			LogRecord.error("TemplateException: " + e.toString());
		}
		this.taskExecutor.execute(new SendMailThread(toEmail, title, text));
	}

	// 内部线程类，利用线程池异步发邮件。
	private class SendMailThread implements Runnable {
		private String to;
		private String subject;
		private String content;

		private SendMailThread(String to, String subject, String content) {
			super();
			this.to = to;
			this.subject = subject;
			this.content = content;
		}

		public void run() {
			sendMail(to, subject, content);
		}
	}

	/**
	 * 发送邮件
	 * 
	 * @param to
	 *            收件人邮箱
	 * @param subject
	 *            邮件主题
	 * @param content
	 *            邮件内容
	 */
	public void sendMail(String to, String subject, String content) {
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message,
					true, "UTF-8");
			messageHelper.setFrom("resume@kdwork.com");
			if (subject != null) {
				messageHelper.setSubject(subject);
			} else {
				messageHelper.setSubject(subject);
			}
			messageHelper.setTo(to);
			messageHelper.setText(content, true);
			javaMailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}

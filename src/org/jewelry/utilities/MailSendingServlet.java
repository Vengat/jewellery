package org.jewelry.utilities;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.*;

/**
 * Servlet implementation class MailSendingServlet
 */
@WebServlet(description = "Sending E-mail Using Session Looked Up as a JNDI Resource", urlPatterns = { "/MailSendingServlet" })
public class MailSendingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Session session;
       
 
	public void init() throws ServletException {
		try {
			Context initContext = new InitialContext(); 
			Context envContext =
				(Context)initContext.lookup("java:/comp/env"); 
			this.session =
				(Session)envContext.lookup("mail/testEmailSession"); 
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String emailRecipient = request.getParameter("sendTo");
		try {
				Message msg = new MimeMessage(this.session);
				msg.setFrom(new InternetAddress("no-reply@tomcat7.jewellery.com"));
				msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailRecipient, false));
				msg.setSubject("Test email");
				msg.setText("Hello and welcome to jewellery mailing list!");
				msg.setSentDate(new Date());
				Transport.send(msg);
				System.out.println("Message sent OK.");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
	}

}

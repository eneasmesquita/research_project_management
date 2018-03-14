package br.projetos.propes.mail;

import java.util.Properties;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {

    private String mailSMTPServer;
    private String mailSMTPServerPort;
    private String user;
    private String password;

    /*
	 * quando instanciar um Objeto ja sera atribuido o servidor SMTP do GMAIL e
	 * a porta usada por ele
     */
    public SendMail() { // Para o GMAIL
        mailSMTPServer = "smtp.gmail.com";
        mailSMTPServerPort = "465";
    }

    /*
	 * caso queira mudar o servidor e a porta, so enviar para o contrutor os
	 * valor como string
     */
    public SendMail(String mailSMTPServer, String mailSMTPServerPort) { // Para outro Servidor
        this.mailSMTPServer = mailSMTPServer;
        this.mailSMTPServerPort = mailSMTPServerPort;
    }

    public Boolean sendMail(String remetente, String destinatario, String assunto, String mensagem, String usuario, String senha) {

        this.user = usuario;
        this.password = senha;
        Properties props = new Properties();
        /**
         * Parâmetros de conexão com servidor Gmail
         */
        props.put("mail.smtp.host", mailSMTPServer);
        props.put("mail.smtp.socketFactory.port", mailSMTPServerPort);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("smtp.starttls.enable", "true");
        props.put("mail.smtp.tls", "true");
        props.put("mail.smtp.port", "465");

        // quem estiver utilizando um SERVIDOR PROXY descomente essa parte e atribua as propriedades do SERVIDOR PROXY utilizado
        props.setProperty("proxySet", "true");
        props.setProperty("socksProxyHost", "0.0.0.0"); // IP do Servidor Proxy _Sigiloso
        props.setProperty("socksProxyPort", "0000"); // Porta do servidor Proxy _Sigiloso

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        /**
         * Ativa Debug para sessão
         */
        session.setDebug(false);
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remetente)); // Remetente

            Address[] toUser = InternetAddress // Destinatário(s)
                    .parse(destinatario);

            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setSubject(assunto);// Assunto
            message.setContent(mensagem, "text/html");
            Transport.send(message);

            return true;

        } catch (MessagingException e) {
            return false;
        }
    }
}

// classe que retorna uma autenticacao para ser enviada e verificada pelo
// servidor smtp
class SimpleAuth extends Authenticator {

    public String username = null;
    public String password = null;

    public SimpleAuth(String user, String pwd) {
        username = user;
        password = pwd;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
    }
}

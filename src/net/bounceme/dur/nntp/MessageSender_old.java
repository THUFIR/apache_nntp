package net.bounceme.dur.nntp;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MessageSender_old {

    private final static Logger LOG = Logger.getLogger(MessageSender_old.class.getName());
    private String header;
    private String body;
    private Properties p;
    private Session session;
    private MimeMessage message;

    private MessageSender_old() {
    }

    public MessageSender_old(Properties p, String... s) throws Exception {
        header = s[0];
        body = s[1];
        this.p = p;
        populate();
    }

    private void populate() throws Exception {
        String lines[] = header.split("\\n");
        session = Session.getDefaultInstance(p, null);
        message = new MimeMessage(session);
        LOG.fine("\n\n\n\nnew message************\n\n\n\n");

        for (String s : lines) {
            if (!s.contains("comp.lang.java.help")) {
                message.addHeaderLine(s);
            }
        }
        message.setContent(message, body);
        String recipient = p.getProperty("recipient");
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(recipient));
        try {
            send();
        } catch (javax.mail.internet.ParseException e) {
            LOG.warning(e.toString());
        } catch (com.sun.mail.smtp.SMTPAddressFailedException e) {
            LOG.warning(e.toString());
            List<Address> addresses = Arrays.asList(message.getAllRecipients());
            for (Address a : addresses) {
                LOG.info(a.toString());
            }
        }
    }

    private void send() throws Exception {
        String protocol = p.getProperty("protocol");
        String host = p.getProperty("host");
        int port = Integer.valueOf(p.getProperty("port"));
        String username = p.getProperty("username");
        String password = p.getProperty("password");
        Transport transport = session.getTransport(protocol);
        LOG.log(Level.FINE, "{0}{1}{2}{3}{4}", new Object[]{protocol, host, port, username, password});
        Enumeration enumOfHeaders = message.getAllHeaders();
        while (enumOfHeaders.hasMoreElements()) {
            Header h = (Header) enumOfHeaders.nextElement();
            LOG.log(Level.FINE, "\n\n\nHEADER\n{0}\n{1}", new Object[]{h.getName(), h.getValue()});
        }
        transport.connect(host, port, username, password);
        transport.sendMessage(message, message.getAllRecipients());
    }
}

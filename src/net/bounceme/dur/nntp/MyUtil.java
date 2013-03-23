package net.bounceme.dur.nntp;

import java.io.BufferedReader;
import java.util.Properties;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.mail.util.MimeMessageUtils;

class MyUtil {

    private final static Logger LOG = Logger.getLogger(MyUtil.class.getName());
    private static Session session;
    private static Transport transport;

    static void send(Properties p, String headers, String body) throws Exception {
        String messageString = headers + body;

        MimeMessage message = MimeMessageUtils.createMimeMessage(session, messageString);
        message.setRecipients(RecipientType.TO, p.getProperty("recipient"));
        Address me = new InternetAddress("foo@whatever.com");
        message.setFrom(me);





        session = Session.getDefaultInstance(p);

        LOG.severe("mail.mime.address is " + session.getProperty("mail.mime.address.strict"));

        String protocol = p.getProperty("protocol");
        String host = p.getProperty("host");
        int port = Integer.valueOf(p.getProperty("port"));
        String username = p.getProperty("username");
        String password = p.getProperty("password");

        transport = session.getTransport(protocol);
        transport.connect(host, port, username, password);
        transport.sendMessage(message, message.getAllRecipients());
    }

    public static String read(BufferedReader br, boolean appendNewLine) throws Exception {
        StringBuilder lines = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            lines.append(line);
            if (appendNewLine) {
                lines.append("\n");
            }
        }
        br.close();
        return new String(lines);
    }
}

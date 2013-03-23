package net.bounceme.dur.nntp;

import java.util.logging.Logger;
import java.util.Properties;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import org.apache.commons.mail.util.MimeMessageUtils;

public class MessageSender {

    private final static Logger LOG = Logger.getLogger(MessageSender.class.getName());

    private MessageSender() {
    }

    public MessageSender(Session session,Properties p, String... s) throws Exception {
        String header = s[0];
        String body = s[1];
        StringBuilder sb = new StringBuilder();
        sb.append(header).append(body);
        MimeMessage foo = MimeMessageUtils.createMimeMessage(session, sb.toString());
    }
}

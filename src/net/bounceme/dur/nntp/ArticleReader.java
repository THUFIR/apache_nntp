package net.bounceme.dur.nntp;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.logging.Logger;
import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import org.apache.commons.mail.util.MimeMessageUtils;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.nntp.NNTPClient;
import org.apache.commons.net.nntp.NewsgroupInfo;

public final class ArticleReader {

    private final static Logger LOG = Logger.getLogger(ArticleReader.class.getName());

    public ArticleReader(Properties p) throws Exception {
        NNTPClient client = new NNTPClient();
        client.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out), true));
        client.connect(p.getProperty("host"));
        NewsgroupInfo newsgroupInfo = new NewsgroupInfo();
        client.selectNewsgroup(p.getProperty("newsgroup"), newsgroupInfo);

        boolean appendNewLine = true;//clumsy
        for (long i = newsgroupInfo.getFirstArticleLong(); i < newsgroupInfo.getLastArticleLong(); i++) {
            appendNewLine = true;
        //    MyUtil.setHeaders();
            String headers = MyUtil.read(client.retrieveArticleHeader(i), appendNewLine);
            appendNewLine = false;
            String body = MyUtil.read(client.retrieveArticleBody(i), appendNewLine);
            MyUtil.send(p, headers, body);
        }
    }

}

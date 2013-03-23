package net.bounceme.dur.nntp;

import java.util.Properties;
import java.util.logging.Logger;

public class Driver {

    private final static Logger LOG = Logger.getLogger(Driver.class.getName());

    public Driver() throws Exception {
        Properties props = new Properties();
        props.load(Driver.class.getResourceAsStream("/nntp.properties"));
        ArticleReader ar = new ArticleReader(props);
    }

    public static void main(String... args) throws Exception {
        new Driver();
    }
}

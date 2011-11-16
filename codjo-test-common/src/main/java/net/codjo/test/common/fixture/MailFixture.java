package net.codjo.test.common.fixture;
import com.dumbster.smtp.SimpleSmtpServer;
import com.dumbster.smtp.SmtpMessage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import junit.framework.Assert;
/**
 *
 */
public class MailFixture implements Fixture {
    public static final int DEFAULT_SMTP_PORT = 25;
    public static final String MAIL_SMTP_HOST_PROPERTY = "mail.smtp.host";
    public static final String MAIL_SMTP_PORT_PROPERTY = "mail.smtp.port";

    static final String FIXTURE_NOT_STARTED_MESSAGE
          = "Mail server has not started yet, doSetup() method should be called before";

    private SimpleSmtpServer server;

    private int mailPort = DEFAULT_SMTP_PORT;


    public MailFixture() {
    }


    public MailFixture(int mailPort) {
        this.mailPort = mailPort;
    }


    public void doSetUp() {
        server = SimpleSmtpServer.start(mailPort);
        Properties props = System.getProperties();
        props.setProperty(MAIL_SMTP_HOST_PROPERTY, "localhost");
        props.setProperty(MAIL_SMTP_PORT_PROPERTY, Integer.toString(mailPort));
    }


    public List<MailMessage> getReceivedMessages() {
        checkMailServerInit();
        return toList(server.getReceivedEmail());
    }


    public MailMessage getReceivedMessage(int index) {
        return getReceivedMessages().get(index);
    }


    public void assertReceivedMessagesCount(int count) {
        Assert.assertEquals("Received message count", count, server.getReceivedEmailSize());
    }


    public void doTearDown() {
        if (server != null) {
            server.stop();
            server = null;
        }
    }


    public int getMailPort() {
        return mailPort;
    }


    private List<MailMessage> toList(Iterator receivedEmail) {
        List<MailMessage> result = new ArrayList<MailMessage>();
        while (receivedEmail.hasNext()) {
            result.add(new MailMessage((SmtpMessage)receivedEmail.next()));
        }
        return result;
    }


    private void checkMailServerInit() {
        if (server == null) {
            throw new IllegalStateException(FIXTURE_NOT_STARTED_MESSAGE);
        }
    }
}

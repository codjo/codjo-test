package net.codjo.test.common.fixture;
import net.codjo.test.common.AssertUtil;
import com.dumbster.smtp.SmtpMessage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import javax.mail.internet.MimeUtility;
import junit.framework.Assert;
import junit.framework.AssertionFailedError;
/**
 *
 */
public class MailMessage {
    private final SmtpMessage smtpMessage;


    public MailMessage(SmtpMessage smtpMessage) {
        this.smtpMessage = smtpMessage;
    }


    public SmtpMessage getSmtpMessage() {
        return smtpMessage;
    }


    public MailMessageAssert assertThat() {
        return new MailMessageAssert();
    }


    public String getFrom() {
        return smtpMessage.getHeaderValue("From");
    }


    public String getTo() {
        return smtpMessage.getHeaderValue("To");
    }


    public String getBody() {
        try {
            String transferEncoding = smtpMessage.getHeaderValue("Content-Transfer-Encoding");
            String encodedBody = smtpMessage.getBody();
            if (encodedBody == null || transferEncoding == null) {
                return null;
            }
            InputStream inputStream =
                  MimeUtility.decode(new ByteArrayInputStream(encodedBody.getBytes()), transferEncoding);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            byte[] bytes = new byte[encodedBody.length()];
            int last = bufferedInputStream.read(bytes);
            return new String(bytes, 0, last);
        }
        catch (Exception e) {
            throw new IllegalStateException("Impossible de décoder le Body : " + e.getMessage(), e);
        }
    }


    public String getSubject() {
        return decodeText(smtpMessage.getHeaderValue("Subject"));
    }


    private String decodeText(String text) {
        try {
            return MimeUtility.decodeText(text);
        }
        catch (UnsupportedEncodingException e) {
            throw new AssertionFailedError("Erreur interne d'encodage : " + e.getMessage());
        }
    }


    @Override
    public String toString() {
        return smtpMessage.toString();
    }


    public class MailMessageAssert {

        public MailMessageAssert from(String expectedFrom) {
            assertHeader(expectedFrom, "From");
            return this;
        }


        public MailMessageAssert to(String expectedTo) {
            return to(new String[]{expectedTo});
        }


        public MailMessageAssert to(String expectedTo1, String expectedTo2) {
            return to(new String[]{expectedTo1, expectedTo2});
        }


        public MailMessageAssert to(String[] expectedTo) {
            String[] toList = smtpMessage.getHeaderValues("To");
            AssertUtil.assertUnorderedEquals("Assert To - ", expectedTo, toList);
            return this;
        }


        public MailMessageAssert subject(String expectedSubject) {
            assertHeader(expectedSubject, "Subject");
            return this;
        }


        public MailMessageAssert bodyContains(String expectedBodyPart) {
            String body = getBody();
            if (!body.contains(expectedBodyPart)) {
                Assert.fail("Assert Body contains -  "
                            + "expected contains:<" + expectedBodyPart + "> "
                            + "but content was:<" + body + ">");
            }
            return this;
        }


        private void assertHeader(String expected, String headerKey) {
            Assert.assertEquals("Assert " + headerKey + " - ",
                                expected,
                                decodeText(smtpMessage.getHeaderValue(headerKey)));
        }
    }
}

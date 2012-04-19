package net.codjo.test.common.fixture;
import com.dumbster.smtp.SmtpMessage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import junit.framework.Assert;
import junit.framework.AssertionFailedError;
import net.codjo.test.common.AssertUtil;
/**
 *
 */
public class MailMessage {
    private static final String CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding";
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
            String encodedBody = smtpMessage.getBody();

            if (encodedBody == null) {
                return null;
            }

            String contentType = smtpMessage.getHeaderValue("Content-Type");

            if ((contentType != null) && (contentType.startsWith("multipart/"))) {
                return decodeMultipart(0);
            }

            return decodeSimpleBody(encodedBody, smtpMessage.getHeaderValue(CONTENT_TRANSFER_ENCODING));
        }
        catch (Exception e) {
            throw new IllegalStateException("Impossible de décoder le Body : " + e.getMessage(), e);
        }
    }


    public File getMultipart(int part, String filePath) {
        if (part == 0) {
            throw new IllegalStateException("La partie '0' est réservée au body. Utilisez plutôt la méthode getBody().");
        }
        try {
            return generateFileFromAttachment(part, filePath);
        }
        catch (Exception e) {
            throw new IllegalStateException(
                  "Impossible de décoder la pièce jointe numéro " + part + ". " + e.getMessage(), e);
        }
    }


    private File generateFileFromAttachment(int part, String filePath) throws MessagingException, IOException {
        MimeMultipart mimeMultipart = new MimeMultipart(new SmtpMessageDataSource(smtpMessage));
        if (part < 0 || part > mimeMultipart.getCount() - 1) {
            throw new IllegalStateException(
                  "L'index de pièce jointe spécifié (" + part + ") est erroné (nombre de pièces jointes : "
                  + (mimeMultipart.getCount() - 1) + ")");
        }

        javax.mail.BodyPart bodyPart = mimeMultipart.getBodyPart(part);

        InputStream source = bodyPart.getInputStream();

        BufferedOutputStream destination = null;
        try {
            destination = new BufferedOutputStream(new FileOutputStream(filePath));

            int bufferSize =
                  (source.available() < 1000000) ? source.available() : 1000000;
            byte[] buf = new byte[bufferSize];

            int bytesRead;
            while (source.available() != 0) {
                bytesRead = source.read(buf);

                destination.write(buf, 0, bytesRead);
            }
            destination.flush();
        }
        finally {
            source.close();
            if (destination != null) {
                destination.close();
            }
        }
        return new File(filePath);
    }


    private String decodeMultipart(int part) throws MessagingException, IOException {
        MimeMultipart mimeMultipart = new MimeMultipart(new SmtpMessageDataSource(smtpMessage));
        javax.mail.BodyPart bodyPart = mimeMultipart.getBodyPart(part);

        BufferedInputStream bufferedInputStream = new BufferedInputStream(bodyPart.getInputStream());
        byte[] bytes = new byte[bodyPart.getSize()];
        int last = bufferedInputStream.read(bytes);
        return new String(bytes, 0, last);
    }


    private String decodeSimpleBody(String encodedBody, String encoding)
          throws MessagingException, IOException {
        if (encoding == null) {
            return null;
        }

        InputStream inputStream = MimeUtility.decode(new ByteArrayInputStream(encodedBody.getBytes()), encoding);

        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        byte[] bytes = new byte[encodedBody.length()];
        int last = bufferedInputStream.read(bytes);
        return new String(bytes, 0, last);
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

    private class SmtpMessageDataSource implements DataSource {
        private SmtpMessage message;


        private SmtpMessageDataSource(SmtpMessage message) {
            this.message = message;
        }


        public String getContentType() {
            String contentType = message.getHeaderValue("Content-Type");
            return contentType == null
                   ? "text/plain"
                   : contentType;
        }


        public String getName() {
            return message.getHeaderValue("subject");
        }


        public InputStream getInputStream() throws IOException {
            String body = message.toString();
// decode the string using platform default encoding because that's how the SmtpMessage was created
            return new ByteArrayInputStream(body.getBytes());
        }


        public OutputStream getOutputStream() throws IOException {
            throw new UnsupportedOperationException();
        }
    }
}

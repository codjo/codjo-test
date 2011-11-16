package net.codjo.test.common;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import junit.framework.TestCase;
/**
 *
 */
public class DateUtilTest extends TestCase {
    public void test_createDate() throws Exception {
        assertEquals(new SimpleDateFormat(DateUtil.FORMAT).parse("2006-08-06"),
                     DateUtil.createDate("2006-08-06"));
    }


    public void test_createDate_failure() throws Exception {
        try {
            DateUtil.createDate("20/06/-2016");
            fail("Mauvais format");
        }
        catch (ParseException e) {
            // good
        }
    }


    public void test_createDateString() {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(Calendar.YEAR, 2012);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 15);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        assertEquals("2012-01-15", DateUtil.createDateString(calendar.getTime()));
    }
}

package net.codjo.test.common.excel.matchers;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import junit.framework.TestCase;
/**
 *
 */
public class CellValueStringifierTest extends TestCase {

    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String TEXT_TODAY = "${TODAY}";
    private static final String TEXT_TODAY_PLUS_ONE_MONTH = "${TODAY}+1M";
    private static final String TEXT_BEFORE_DATE_PLUS_ONE_MONTH = "TEXT BEFORE ${TODAY}+1M";
    private static final String TEXT_BEFORE_DATE_TODAY = "TEXT BEFORE ${TODAY}";
    private static final String TEXT_AFTER_DATE_PLUS_ONE_MONTH = "${TODAY}+1M TEXT AFTER";
    private static final String TEXT_AFTER_DATE_TODAY = "${TODAY} TEXT AFTER";

    private CellValueStringifier stringifier = new CellValueStringifier();


    public void test_isDateRelative() {
        assertTrue(stringifier.isDateRelative(TEXT_BEFORE_DATE_PLUS_ONE_MONTH));
        assertTrue(stringifier.isDateRelative(TEXT_BEFORE_DATE_TODAY));
        assertTrue(stringifier.isDateRelative("${TODAY} TEXT AFTER"));
        assertTrue(stringifier.isDateRelative("TEXT BEFORE ${TODAY} TEXT AFTER"));
        assertTrue(stringifier.isDateRelative("TEXT BEFORE ${TODAY}+1D TEXT AFTER"));
        assertTrue(stringifier.isDateRelative("${TODAY}+1D"));
        assertTrue(stringifier.isDateRelative("${TODAY}+10M"));
        assertTrue(stringifier.isDateRelative("${TODAY}+100Y"));
        assertTrue(stringifier.isDateRelative("${TODAY}-2D"));
        assertTrue(stringifier.isDateRelative("${TODAY}-20M"));
        assertTrue(stringifier.isDateRelative("${TODAY}-200Y"));
        assertTrue(stringifier.isDateRelative("${TODAY}"));
        assertFalse(stringifier.isDateRelative("${TOUDAY}"));

//        assertTrue(stringifier.isDateRelative("${TODAY}+1D+1D"));
//        assertFalse(stringifier.isDateRelative("${TODAY}${TODAY}+1D"));
//        assertFalse(stringifier.isDateRelative("${TODAY}*100D"));
//        assertFalse(stringifier.isDateRelative("${TODAY}*15Z"));
//        assertFalse(stringifier.isDateRelative("${TODAY}+D"));
//        assertFalse(stringifier.isDateRelative("${TODAY}+1"));
//        assertFalse(stringifier.isDateRelative("${TODAY}+01K"));
//        assertFalse(stringifier.isDateRelative("${TODAY} + 1 D"));
    }


    public void test_computeRelativeDate() {

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, 1);
        String todayPlusOneMonth = new SimpleDateFormat(DATE_FORMAT).format(calendar.getTime());
        calendar.setTime(new Date());
        String today = new SimpleDateFormat(DATE_FORMAT).format(calendar.getTime());

        if (stringifier.isDateRelative(TEXT_TODAY)) {
            assertEquals(today, stringifier.computeRelativeDate(TEXT_TODAY));
        }

        if (stringifier.isDateRelative(TEXT_TODAY_PLUS_ONE_MONTH)) {
            assertEquals(todayPlusOneMonth, stringifier.computeRelativeDate(TEXT_TODAY_PLUS_ONE_MONTH));
        }

        if (stringifier.isDateRelative(TEXT_BEFORE_DATE_PLUS_ONE_MONTH)) {
            assertEquals("TEXT BEFORE " + todayPlusOneMonth,
                         stringifier.computeRelativeDate(TEXT_BEFORE_DATE_PLUS_ONE_MONTH));
        }

        if (stringifier.isDateRelative(TEXT_BEFORE_DATE_TODAY)) {
            assertEquals("TEXT BEFORE " + today, stringifier.computeRelativeDate(TEXT_BEFORE_DATE_TODAY));
        }

        if (stringifier.isDateRelative(TEXT_AFTER_DATE_PLUS_ONE_MONTH)) {
            assertEquals(todayPlusOneMonth + " TEXT AFTER",
                         stringifier.computeRelativeDate(TEXT_AFTER_DATE_PLUS_ONE_MONTH));
        }

        if (stringifier.isDateRelative(TEXT_AFTER_DATE_TODAY)) {
            assertEquals(today + " TEXT AFTER", stringifier.computeRelativeDate(TEXT_AFTER_DATE_TODAY));
        }
    }
}

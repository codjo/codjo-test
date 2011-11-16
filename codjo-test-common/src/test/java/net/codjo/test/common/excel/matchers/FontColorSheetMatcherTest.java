package net.codjo.test.common.excel.matchers;
import net.codjo.test.common.excel.ExcelMatchingException;
import net.codjo.test.common.excel.ExcelUtil;
import java.io.File;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Assert;
import static org.junit.Assert.fail;
import org.junit.Test;
/**
 *
 */
public class FontColorSheetMatcherTest {

    private FontColorSheetMatcher matcher = new FontColorSheetMatcher();


    @Test
    public void test_matchOk() throws Exception {

        HSSFWorkbook expectedWorkbook
              = ExcelUtil.loadWorkbook(new File(FontColorSheetMatcherTest.class.getResource(
              "expected_fontcolor.xls").toURI()));
        HSSFWorkbook actualWorkbook
              = ExcelUtil.loadWorkbook(new File(FontColorSheetMatcherTest.class.getResource(
              "actual_fontcolor_ok.xls").toURI()));

        matcher.match("mySheet", expectedWorkbook, actualWorkbook);
    }


    @Test
    public void test_matchKo() throws Exception {

        HSSFWorkbook expectedWorkbook
              = ExcelUtil.loadWorkbook(new File(FontColorSheetMatcherTest.class.getResource(
              "expected_fontcolor.xls").toURI()));
        HSSFWorkbook actualWorkbook
              = ExcelUtil.loadWorkbook(new File(FontColorSheetMatcherTest.class.getResource(
              "actual_fontcolor_ko.xls").toURI()));

        try {
            matcher.match("mySheet", expectedWorkbook, actualWorkbook);
            fail();
        }
        catch (ExcelMatchingException err) {
            Assert.assertEquals("Couleur de la font des cellules de la feuille 'mySheet' en erreur.\n"
                                + "Excel line 1\n"
                                + "\tExpected > RED | RED |\n"
                                + "\tActual   > AUTOMATIC | AUTOMATIC |\n"
                                + "\tGap      > *\n", err.getMessage());
        }
    }
}
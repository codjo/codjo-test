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
public class BackgroundColorSheetMatcherTest {

    private BackgroundColorSheetMatcher matcher = new BackgroundColorSheetMatcher();


    @Test
    public void test_matchOk() throws Exception {

        HSSFWorkbook expectedWorkbook
              = ExcelUtil.loadWorkbook(new File(BackgroundColorSheetMatcherTest.class.getResource(
              "expected_backgroundcolor.xls").toURI()));
        HSSFWorkbook actualWorkbook
              = ExcelUtil.loadWorkbook(new File(BackgroundColorSheetMatcherTest.class.getResource(
              "actual_backgroundcolor_ok.xls").toURI()));

        matcher.match("mySheet", expectedWorkbook, actualWorkbook);
    }


    @Test
    public void test_matchKo() throws Exception {

        HSSFWorkbook expectedWorkbook
              = ExcelUtil.loadWorkbook(new File(BackgroundColorSheetMatcherTest.class.getResource(
              "expected_backgroundcolor.xls").toURI()));
        HSSFWorkbook actualWorkbook
              = ExcelUtil.loadWorkbook(new File(BackgroundColorSheetMatcherTest.class.getResource(
              "actual_backgroundcolor_ko.xls").toURI()));

        try {
            matcher.match("mySheet", expectedWorkbook, actualWorkbook);
            fail();
        }
        catch (ExcelMatchingException err) {
            Assert.assertEquals("Couleur de fond des cellules de la feuille 'mySheet' en erreur.\n"
                                + "Excel line 1\n"
                                + "\tExpected > GREY_40_PERCENT | GREY_40_PERCENT |\n"
                                + "\tActual   > BLUE | BLUE |\n"
                                + "\tGap      > *\n", err.getMessage());
        }
    }
}

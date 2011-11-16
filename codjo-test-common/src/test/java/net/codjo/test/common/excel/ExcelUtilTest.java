package net.codjo.test.common.excel;
import java.io.File;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;
/**
 *
 */
public class ExcelUtilTest {

    @Test
    public void test_compareWithBadMatcherNames() throws Exception {
        try {
            ExcelUtil.compare(new File(getClass().getResource("expected_ok.xls").toURI()),
                              new File(getClass().getResource("actual.xls").toURI()),
                              null, "bold,italic,toto,margin");
            fail();
        }
        catch (ExcelMatchingException e) {
            assertEquals("Matcher 'toto' inconnu. Liste des matchers disponibles : \n"
                         + "\t - font-size\n"
                         + "\t - italic\n"
                         + "\t - bold\n"
                         + "\t - margin-size\n"
                         + "\t - alignement\n"
                         + "\t - font-color\n"
                         + "\t - border\n"
                         + "\t - background-color\n"
                         + "\t - merge-region\n"
                  , e.getMessage());
        }
    }
}

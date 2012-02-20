package net.codjo.test.common.excel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import net.codjo.test.common.excel.matchers.AlignmentSheetStyleMatcher;
import net.codjo.test.common.excel.matchers.BackgroundColorSheetMatcher;
import net.codjo.test.common.excel.matchers.BoldSheetStyleMatcher;
import net.codjo.test.common.excel.matchers.BorderSheetStyleMatcher;
import net.codjo.test.common.excel.matchers.ContentSheetMatcher;
import net.codjo.test.common.excel.matchers.FontColorSheetMatcher;
import net.codjo.test.common.excel.matchers.FontSizeSheetStyleMatcher;
import net.codjo.test.common.excel.matchers.ItalicSheetStyleMatcher;
import net.codjo.test.common.excel.matchers.MarginSizeSheetStyleMatcher;
import net.codjo.test.common.excel.matchers.MergeRegionSheetStyleMatcher;
import net.codjo.test.common.excel.matchers.SheetMatcher;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelComparator {

    private static final SheetMatcher CONTENT_SHEET_MATCHER = new ContentSheetMatcher();

    public static final Map<String, SheetMatcher> STYLE_MATCHER_LIST = new HashMap<String, SheetMatcher>();


    static {
        STYLE_MATCHER_LIST.put(MergeRegionSheetStyleMatcher.NAME, new MergeRegionSheetStyleMatcher());
        STYLE_MATCHER_LIST.put(BoldSheetStyleMatcher.NAME, new BoldSheetStyleMatcher());
        STYLE_MATCHER_LIST.put(ItalicSheetStyleMatcher.NAME, new ItalicSheetStyleMatcher());
        STYLE_MATCHER_LIST.put(FontSizeSheetStyleMatcher.NAME, new FontSizeSheetStyleMatcher());
        STYLE_MATCHER_LIST.put(AlignmentSheetStyleMatcher.NAME, new AlignmentSheetStyleMatcher());
        STYLE_MATCHER_LIST.put(BorderSheetStyleMatcher.NAME, new BorderSheetStyleMatcher());
        STYLE_MATCHER_LIST.put(MarginSizeSheetStyleMatcher.NAME, new MarginSizeSheetStyleMatcher());
        STYLE_MATCHER_LIST.put(BackgroundColorSheetMatcher.NAME, new BackgroundColorSheetMatcher());
        STYLE_MATCHER_LIST.put(FontColorSheetMatcher.NAME, new FontColorSheetMatcher());
    }


    private ExcelComparator() {
    }


    public static boolean execute(HSSFWorkbook expectedWorkbook,
                                  HSSFWorkbook actualWorkbook,
                                  List<String> sheetsToAssert,
                                  List<SheetMatcher> sheetMatcherList) {

        Map<String, HSSFSheet> actualSheets = getSheetToCompare(actualWorkbook, sheetsToAssert);
        Map<String, HSSFSheet> expectedSheets = getSheetToCompare(expectedWorkbook, sheetsToAssert);

        assertSheetNames(actualSheets.keySet(), expectedSheets.keySet());

        for (Entry<String, HSSFSheet> entry : expectedSheets.entrySet()) {
            final String sheetName = entry.getKey();
            CONTENT_SHEET_MATCHER.match(sheetName, expectedWorkbook, actualWorkbook);
            for (SheetMatcher sheetMatcher : sheetMatcherList) {
                sheetMatcher.match(sheetName, expectedWorkbook, actualWorkbook);
            }
        }
        return true;
    }


    private static void assertSheetNames(Set<String> currentSheetNames, Set<String> expectedSheetNames) {
        if (!new ArrayList<String>(currentSheetNames).equals(new ArrayList<String>(expectedSheetNames))) {
            throw new ExcelMatchingException(
                  "Les deux classeurs Excel ne contiennent pas les mêmes feuilles\n"
                  + "expected = " + StringUtils.join(expectedSheetNames, ", ") + "\n"
                  + "actual   = " + StringUtils.join(currentSheetNames, ", ") + "\n");
        }
    }


    private static Map<String, HSSFSheet> getSheetToCompare(HSSFWorkbook excelWorkbook,
                                                            List<String> sheetNamesToCompare) {
        Map<String, HSSFSheet> sheetsToCompare = new LinkedHashMap<String, HSSFSheet>();

        Filter filter = new DefaultFilter();
        if (!sheetNamesToCompare.isEmpty()) {
            filter = new SheetNameFilter(sheetNamesToCompare);
        }

        for (int i = 0; i < excelWorkbook.getNumberOfSheets(); i++) {
            String sheetName = excelWorkbook.getSheetName(i);
            if (filter.accept(sheetName)) {
                sheetsToCompare.put(sheetName, excelWorkbook.getSheetAt(i));
            }
        }
        return sheetsToCompare;
    }


    public static List<SheetMatcher> buildSheetMatcherList(List<String> matchersToApply) {
        List<SheetMatcher> sheetMatcherList = new ArrayList<SheetMatcher>();

        for (String matcherToApply : matchersToApply) {
            if (!STYLE_MATCHER_LIST.containsKey(matcherToApply)) {

                throw new ExcelMatchingException("Matcher '" + matcherToApply + "' inconnu. "
                                                 + buildAvailableMatcherListString());
            }
            else {
                sheetMatcherList.add(STYLE_MATCHER_LIST.get(matcherToApply));
            }
        }

        return sheetMatcherList;
    }


    private static String buildAvailableMatcherListString() {
        StringBuilder message = new StringBuilder("Liste des matchers disponibles : ");
        Set<String> strings = new TreeSet<String>(STYLE_MATCHER_LIST.keySet());
        for (String matcherName : strings) {
            message.append("\n\t - ").append(matcherName);
        }
        return message.append("\n").toString();
    }


    private interface Filter {

        boolean accept(String sheetName);
    }

    private static class DefaultFilter implements Filter {
        public boolean accept(String sheetName) {
            return true;
        }
    }

    private static class SheetNameFilter implements Filter {
        private final List<String> sheetNamesToCompare;


        private SheetNameFilter(List<String> sheetNamesToCompare) {

            this.sheetNamesToCompare = sheetNamesToCompare;
        }


        public boolean accept(String sheetName) {
            return sheetNamesToCompare.contains(sheetName);
        }
    }
}

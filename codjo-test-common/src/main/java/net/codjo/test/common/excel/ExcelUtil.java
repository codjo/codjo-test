package net.codjo.test.common.excel;
import static net.codjo.test.common.excel.ExcelComparator.buildSheetMatcherList;
import net.codjo.test.common.excel.matchers.Cell;
import net.codjo.test.common.excel.matchers.CellStringifier;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;

public class ExcelUtil {

    private ExcelUtil() {
    }


    public static boolean compare(File expectedFile, File actualFile,
                                  String sheetNamesToCompare, String sheetMatchersToApply) throws Exception {
        HSSFWorkbook expectedWorkbook = ExcelUtil.loadWorkbook(expectedFile);
        HSSFWorkbook totoWorkbook = ExcelUtil.loadWorkbook(actualFile);

        return ExcelComparator.execute(expectedWorkbook,
                                       totoWorkbook,
                                       buildList(sheetNamesToCompare),
                                       buildSheetMatcherList(buildList(sheetMatchersToApply)));
    }


    public static boolean compare(HSSFWorkbook expectedWorkbook, HSSFWorkbook actualWorkbook,
                                  String sheetNamesToCompare, String sheetMatchersToApply) throws Exception {
        return ExcelComparator.execute(expectedWorkbook,
                                       actualWorkbook,
                                       buildList(sheetNamesToCompare),
                                       buildSheetMatcherList(buildList(sheetMatchersToApply)));
    }


    private static List<String> buildList(String sheets) {
        List<String> sheetNamesToCompare = Collections.emptyList();
        if (sheets != null) {
            sheetNamesToCompare = Arrays.asList(sheets.split(","));
        }
        return sheetNamesToCompare;
    }


    public static HSSFWorkbook loadWorkbook(File file) throws IOException {
        file.getAbsolutePath();

        FileInputStream inputStream = new FileInputStream(file);

        try {
            POIFSFileSystem fichierSource = new POIFSFileSystem(inputStream);
            return new HSSFWorkbook(fichierSource);
        }
        finally {
            inputStream.close();
        }
    }


    public static boolean isNumericCellValue(HSSFCell cell) {
        try {
            cell.getNumericCellValue();
            return true;
        }
        catch (IllegalStateException ex) {
            return false;
        }
    }


    public static String toString(HSSFSheet sheet, CellStringifier cellStringifier) {
        StringBuilder builder = new StringBuilder();

        Iterator<Row> rows = sheet.rowIterator();
        final short cellNum = findMaxCellNum(sheet);
        List<List<Cell>> cellAccumulator = new ArrayList<List<Cell>>();

        while (rows.hasNext()) {
            List<Cell> cellList = extractCells((HSSFRow)rows.next(), cellNum);
            Collections.sort(cellList);

            if (isBlankRow(cellList)) {
                cellAccumulator.add(cellList);
            }
            else {
                if (!cellAccumulator.isEmpty()) {
                    for (List<Cell> blankLine : cellAccumulator) {
                        builder.append(toString(blankLine, cellStringifier));
                    }
                    cellAccumulator.clear();
                }
                builder.append(toString(cellList, cellStringifier));
            }
        }

        return builder.toString();
    }


    private static boolean isBlankRow(List<Cell> cellList) {
        for (Cell cell : cellList) {
            if (cell.getPoiCell().getCellType() != HSSFCell.CELL_TYPE_BLANK) {
                return false;
            }
        }
        return true;
    }


    private static short findMaxCellNum(HSSFSheet sheet) {
        Iterator<Row> rows = sheet.rowIterator();
        short maxCellNum = 0;
        while (rows.hasNext()) {
            Row row = rows.next();
            short lastCellNum = row.getLastCellNum();
            if (maxCellNum < lastCellNum) {
                maxCellNum = lastCellNum;
            }
        }
        return maxCellNum;
    }


    private static List<Cell> extractCells(HSSFRow row, short maxCellNum) {
        List<Cell> cells = new ArrayList<Cell>();
        int rowNum = row.getRowNum();

        for (int cellIndex = 0; cellIndex < maxCellNum; cellIndex++) {
            HSSFCell cell = row.getCell(cellIndex, Row.CREATE_NULL_AS_BLANK);
            cells.add(new Cell(rowNum, cell.getColumnIndex(), cell));
        }

//        Iterator cellIterator = row.cellIterator();
//        while (cellIterator.hasNext()) {
//            HSSFCell cell = (HSSFCell)cellIterator.next();
////            if (cell.getCellType() != HSSFCell.CELL_TYPE_BLANK) {
//
//                cells.add(new Cell(rowNum, cell.getColumnIndex(), cell));
////            }
//        }
        return cells;
    }


    public static String toString(List<Cell> cells, CellStringifier cellStringifier) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < cells.size(); i++) {
            Cell cell = cells.get(i);
            if (i > 0) {
                builder.append(" ");
            }
            builder.append(cellStringifier.toString(cell)).append(" |");
        }
        if (builder.length() > 0) {
            builder.append('\n');
        }
        return builder.toString();
    }
}

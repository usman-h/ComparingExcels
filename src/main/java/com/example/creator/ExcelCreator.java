package com.example.creator;

import com.example.compare.Report;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelCreator {
	private static final int ROW_PER_REPORT = 13;

	public static void create(
		String firstFile, String secondFile, List<Report> reportList, String finalFileName) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook();
		copyOriginal(firstFile, workbook);
		copyOriginal(secondFile, workbook);
		final XSSFSheet sheet = workbook.createSheet("report");
		int rowNum = 0;
		for (int i = 0; i < reportList.size(); i++) {
			List<Row> rows = new LinkedList<>();
			final Report currentReport = reportList.get(i);
			rowNum = createRows(sheet, rowNum, rows);
			fillRow(rows, 0, "Issue: " + (i + 1), workbook);
			fillRow(rows, 1, "File: " + getFileName(currentReport.getFirstFileName()));
			final Map<String, String> mapping = currentReport.getRowFirstFile();
			fillRowMapping(2, 3, mapping, rows);
			fillRow(rows, 4, "");
			fillRow(rows, 5, "File: " + getFileName(currentReport.getSecondFileName()));
			final Map<String, String> mapping2 = currentReport.getRowSecondFile();
			fillRowMapping(6, 7, mapping2, rows);
			fillRow(rows, 8, "");
			fillRow(rows, 9, "Difference");
			fillDifference(currentReport, rows);
			fillRow(rows, 12, "");
		}
		saveOutput(finalFileName, workbook);
		System.out.println("Written!");

	}

	private static String getFileName(String fileName) {
		final int i = fileName.lastIndexOf(File.separator);
		return fileName.substring(i + 1);
	}

	private static void fillRow(List<Row> rows, int index, String value, XSSFWorkbook workbook) {
		final Row row = rows.get(index);
		final Cell cell = row.createCell(0);
		if (index == 0) {
			CellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cell.setCellStyle(style);
		}
		cell.setCellValue(value);
	}

	private static void fillDifference(Report currentReport, List<Row> rows) {
		final Row row1 = rows.get(10);
		final Row row2 = rows.get(11);
		final Cell col1 = row1.createCell(0);
		final Cell col3 = row1.createCell(2);
		col1.setCellValue(currentReport.getColumnName());
		col3.setCellValue(currentReport.getColumnName());
		final Cell col11 = row2.createCell(0);
		final Cell col33 = row2.createCell(2);
		col11.setCellValue(currentReport.getColumnValueFirstFile());
		col33.setCellValue(currentReport.getColumnValueSecondFile());
	}

	private static void fillRowMapping(
		int firstRow, int secondRow, Map<String, String> mapping, List<Row> rows) {
		final Row thirdRow = rows.get(firstRow);
		final Row fourthRow = rows.get(secondRow);
		List<String> columnNames = new ArrayList<>(mapping.keySet());
		for (int i1 = 0; i1 < columnNames.size(); i1++) {
			final Cell thirdRowColumnNameCell = thirdRow.createCell(i1);
			final Cell fourthRowColumnValueCell = fourthRow.createCell(i1);
			thirdRowColumnNameCell.setCellValue(columnNames.get(i1));
			fourthRowColumnValueCell.setCellValue(mapping.get(columnNames.get(i1)));
		}
	}

	private static void fillRow(List<Row> rows, int index, String value) {
		fillRow(rows, index, value, null);
	}

	private static void saveOutput(String finalFileName, XSSFWorkbook workbook) {
		try {
			FileOutputStream out = new FileOutputStream(finalFileName);
			workbook.write(out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static int createRows(XSSFSheet sheet, int rowNum, List<Row> rows) {
		for (int j = 0; j < ROW_PER_REPORT; j++) {
			Row row = sheet.createRow(rowNum);
			rows.add(row);
			rowNum++;
		}
		return rowNum;
	}

	private static void copyOriginal(String fileName, XSSFWorkbook myWorkBook) throws IOException {
		FileInputStream asStream = new FileInputStream(Paths.get(fileName).toFile());
		XSSFWorkbook workbook = new XSSFWorkbook(asStream);
		XSSFSheet sheet;
		XSSFRow row;
		XSSFCell cell;
		XSSFSheet mySheet;
		XSSFRow myRow;
		XSSFCell myCell;
		int sheets = workbook.getNumberOfSheets();
		int fCell;
		int lCell;
		int fRow;
		int lRow;
		for (int iSheet = 0; iSheet < sheets; iSheet++) {
			sheet = workbook.getSheetAt(iSheet);
			if (sheet != null) {
				mySheet = myWorkBook.createSheet(getFileName(fileName));
				fRow = sheet.getFirstRowNum();
				lRow = sheet.getLastRowNum();
				for (int iRow = fRow; iRow <= lRow; iRow++) {
					row = sheet.getRow(iRow);
					myRow = mySheet.createRow(iRow);
					if (row != null) {
						fCell = row.getFirstCellNum();
						lCell = row.getLastCellNum();
						for (int iCell = fCell; iCell < lCell; iCell++) {
							cell = row.getCell(iCell);
							myCell = myRow.createCell(iCell);
							if (cell != null) {
								myCell.setCellType(cell.getCellType());
								if (cell.getCellType().equals(CellType.BLANK)) {
									myCell.setCellValue("");
								} else if (cell.getCellType().equals(CellType.BOOLEAN)) {
									myCell.setCellValue(cell.getBooleanCellValue());
								} else if (cell.getCellType().equals(CellType.NUMERIC)) {
									if (DateUtil.isCellDateFormatted(cell)) {
										DateFormat df = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
										Date date = cell.getDateCellValue();
										myCell.setCellValue(df.format(date));
									} else {
										DataFormatter formatter = new DataFormatter();
										myCell.setCellValue(formatter.formatCellValue(cell));
									}
								} else if (cell.getCellType().equals(CellType.STRING)) {
									myCell.setCellValue(cell.getStringCellValue());
								} else if (cell.getCellType().equals(CellType.ERROR)) {
									myCell.setCellValue(cell.getErrorCellValue());
								} else {
									myCell.setCellFormula(cell.getCellFormula());
								}
							}
						}
					}
				}
			}
		}
		asStream.close();
	}
}

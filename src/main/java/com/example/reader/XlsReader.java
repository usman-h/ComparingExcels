package com.example.reader;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class XlsReader {

	public static Map<Integer, Map<String, String>> readData(String fileName) throws IOException {
		Map<Integer, Map<String, String>> values = new LinkedHashMap<>();
		Workbook workbook = null;
		try {
			//InputStream asStream = XlsReader.class.getClassLoader().getResourceAsStream(fileName);
			FileInputStream asStream = new FileInputStream(Paths.get(fileName).toFile());
			workbook = new XSSFWorkbook(asStream);
			final Sheet mainSheet = workbook.getSheetAt(0);
			if (mainSheet == null) {
				throw new InvalidPropertiesFormatException("No sheet defined");
			}
			int rowCounter = 0;
			List<String> columnNames = new LinkedList<>();
			for (final Row currentRow : mainSheet) {
				rowCounter++;
				if (rowCounter == 1) {
					getColumnNames(columnNames, currentRow);
				} else {
					Map<String, String> columnMap = new LinkedHashMap<>();
					for (int columnCount = 0; columnCount < currentRow.getLastCellNum(); columnCount++) {
						Cell cell = currentRow.getCell(columnCount, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
						try {
							fillColumnPerRow(columnNames, columnMap, cell, columnCount);
						} catch (Exception e) {
							//fill empty if any error
							//columnMap.put(columnNames.get(columnCount), "");
						}
					}
					values.put(rowCounter, columnMap);
				}
			}
		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
		return values;
	}

	private static void fillColumnPerRow(
		List<String> columnNames, Map<String, String> columnMap, Cell currentCell, int columnCount) {
		if (currentCell != null) {
			if (currentCell.getCellType() == CellType.STRING) {
				columnMap.put(columnNames.get(columnCount), currentCell.getStringCellValue());
			} else if (currentCell.getCellType() == CellType.NUMERIC) {
				if (DateUtil.isCellDateFormatted(currentCell)) {
					DateFormat df = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
					Date date = currentCell.getDateCellValue();
					columnMap.put(columnNames.get(columnCount), df.format(date));
				} else {
					DataFormatter formatter = new DataFormatter();
					columnMap.put(columnNames.get(columnCount), formatter.formatCellValue(currentCell));
				}
			} else if (currentCell.getCellType() == CellType.BLANK) {
				//fill empty if any blank
				columnMap.put(columnNames.get(columnCount), "");
			} else {
				//fill empty if any other case
				columnMap.put(columnNames.get(columnCount), "");
			}
		}
	}

	private static void getColumnNames(List<String> columnNames, Row currentRow) {
		for (final Cell currentCell : currentRow) {
			if (currentCell != null && currentCell.getCellType() != CellType.BLANK &&
				    currentCell.getCellType() == CellType.STRING) {
				columnNames.add(currentCell.getStringCellValue());
			}
		}
	}
}

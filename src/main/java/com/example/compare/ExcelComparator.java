package com.example.compare;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ExcelComparator {
	public static List<Report> compare(
		Map<Integer, Map<String, String>> firstData,
		Map<Integer, Map<String, String>> secondData, String firstFileName, String secondFileName) {
		List<Report> reportList = new LinkedList<>();
		for (int rowNumber : firstData.keySet()) {
			final Map<String, String> rowDataFirstExcel = firstData.get(rowNumber);
			if (secondData.containsKey(rowNumber)) {
				final Map<String, String> rowDataSecondExcel = secondData.get(rowNumber);
				for (String columnName : rowDataFirstExcel.keySet()) {
					if (rowDataSecondExcel.containsKey(columnName)) {
						final String columnValueFirstExcel = rowDataFirstExcel.get(columnName);
						final String columnValueSecondExcel = rowDataSecondExcel.get(columnName);
						if (!columnValueFirstExcel.equals(columnValueSecondExcel)) {
							Report report = new Report();
							report.setColumnName(columnName);
							report.setColumnValueFirstFile(columnValueFirstExcel);
							report.setColumnValueSecondFile(columnValueSecondExcel);
							report.setFirstFileName(firstFileName);
							report.setSecondFileName(secondFileName);
							report.setRowFirstFile(rowDataFirstExcel);
							report.setRowSecondFile(rowDataSecondExcel);
							reportList.add(report);
						}
					}
				}
			}
		}
		return reportList;
	}
}

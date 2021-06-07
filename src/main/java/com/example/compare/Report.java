package com.example.compare;

import java.util.Map;
import java.util.Objects;

public class Report {
	private String firstFileName;
	private String secondFileName;
	private Map<String, String> rowFirstFile;
	private Map<String, String> rowSecondFile;
	private String columnName;
	private String columnValueFirstFile;
	private String columnValueSecondFile;

	public Report(
		String firstFileName, String secondFileName, Map<String, String> rowFirstFile, Map<String, String> rowSecondFile,
		String columnName, String columnValueFirstFile, String columnValueSecondFile) {
		this.firstFileName = firstFileName;
		this.secondFileName = secondFileName;
		this.rowFirstFile = rowFirstFile;
		this.rowSecondFile = rowSecondFile;
		this.columnName = columnName;
		this.columnValueFirstFile = columnValueFirstFile;
		this.columnValueSecondFile = columnValueSecondFile;
	}

	public Report() {
	}

	public String getFirstFileName() {
		return firstFileName;
	}

	public void setFirstFileName(String firstFileName) {
		this.firstFileName = firstFileName;
	}

	public String getSecondFileName() {
		return secondFileName;
	}

	public void setSecondFileName(String secondFileName) {
		this.secondFileName = secondFileName;
	}

	public Map<String, String> getRowFirstFile() {
		return rowFirstFile;
	}

	public void setRowFirstFile(Map<String, String> rowFirstFile) {
		this.rowFirstFile = rowFirstFile;
	}

	public Map<String, String> getRowSecondFile() {
		return rowSecondFile;
	}

	public void setRowSecondFile(Map<String, String> rowSecondFile) {
		this.rowSecondFile = rowSecondFile;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnValueFirstFile() {
		return columnValueFirstFile;
	}

	public void setColumnValueFirstFile(String columnValueFirstFile) {
		this.columnValueFirstFile = columnValueFirstFile;
	}

	public String getColumnValueSecondFile() {
		return columnValueSecondFile;
	}

	public void setColumnValueSecondFile(String columnValueSecondFile) {
		this.columnValueSecondFile = columnValueSecondFile;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Report report = (Report) o;
		return Objects.equals(firstFileName, report.firstFileName) &&
			       Objects.equals(secondFileName, report.secondFileName) &&
			       Objects.equals(rowFirstFile, report.rowFirstFile) &&
			       Objects.equals(rowSecondFile, report.rowSecondFile) &&
			       Objects.equals(columnName, report.columnName)
			       && Objects.equals(columnValueFirstFile, report.columnValueFirstFile) &&
			       Objects.equals(columnValueSecondFile, report.columnValueSecondFile);
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstFileName, secondFileName, rowFirstFile, rowSecondFile,
			columnName, columnValueFirstFile, columnValueSecondFile);
	}
}

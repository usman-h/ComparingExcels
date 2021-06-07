import com.example.compare.ExcelComparator;
import com.example.compare.Report;
import com.example.creator.ExcelCreator;
import com.example.reader.XlsReader;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Tester {

	private static final String FIRST_FILE_NAME = "C:\\Users\\usman\\Downloads\\excelcompare_Final\\excelcompare\\src\\main\\resources\\Snowflake.xlsx";
	private static final String SECOND_FILE_NAME = "C:\\Users\\usman\\Downloads\\excelcompare_Final\\excelcompare\\src\\main\\resources\\Snowflake2.xlsx";
	private static final String FINAL_FILE_NAME = "C:\\Users\\usman\\Downloads\\excelcompare_Final\\excelcompare\\src\\main\\resources\\Results\\ReconResults.xlsx";

	public static void main(String[] args) throws IOException {
		final Map<Integer, Map<String, String>> firstData = XlsReader.readData(FIRST_FILE_NAME);
		final Map<Integer, Map<String, String>> secondData = XlsReader.readData(SECOND_FILE_NAME);
		final List<Report> report = ExcelComparator.compare(firstData, secondData,
			FIRST_FILE_NAME, SECOND_FILE_NAME);
		ExcelCreator.create(FIRST_FILE_NAME, SECOND_FILE_NAME, report, FINAL_FILE_NAME);
	}
}

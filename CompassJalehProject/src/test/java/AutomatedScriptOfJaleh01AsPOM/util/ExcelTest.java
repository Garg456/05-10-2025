package AutomatedScriptOfJaleh01AsPOM.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelTest {

    public static List<Map<String, String>> readData(String filePath, String sheetName) throws IOException {
        List<Map<String, String>> testData = new ArrayList<>();
        
        try (FileInputStream file = new FileInputStream(filePath);
             XSSFWorkbook workbook = new XSSFWorkbook(file)) {

            XSSFSheet sheet = workbook.getSheet(sheetName);
            XSSFRow headerRow = sheet.getRow(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                XSSFRow currentRow = sheet.getRow(i);
                if (currentRow == null) continue;

                Map<String, String> dataMap = new HashMap<>();
                for (int j = 0; j < headerRow.getLastCellNum(); j++) {
                    String key = headerRow.getCell(j).getStringCellValue();
                    String value = "";
                    if (currentRow.getCell(j) != null) {
                        value = currentRow.getCell(j).toString();
                    }
                    dataMap.put(key, value);
                }

                testData.add(dataMap);
            }
        }
        return testData;
    }
}

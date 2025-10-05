package compass.commonclass;

import java.io.*;
import java.time.Duration;
import java.util.*;
import org.apache.poi.xssf.usermodel.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    protected void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterMethod
    protected void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // ✅ Excel Reader
    public static List<Map<String, String>> readdata() throws IOException {
        List<Map<String, String>> testData = new ArrayList<>();
        try (FileInputStream file = new FileInputStream("E:\\Automation Testing data\\Jaleh\\test1_creation_Master.xlsx");
             XSSFWorkbook workbook = new XSSFWorkbook(file)) {

            XSSFSheet sheet = workbook.getSheetAt(0);
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

    // ✅ DataProvider
    @DataProvider(name = "loginData")
    public Object[][] loginDataProvider() throws IOException {
        List<Map<String, String>> dataList = readdata();
        Object[][] data = new Object[dataList.size()][1];

        for (int i = 0; i < dataList.size(); i++) {
            data[i][0] = dataList.get(i);  // send the entire map as one object
        }

        return data;
    }
}

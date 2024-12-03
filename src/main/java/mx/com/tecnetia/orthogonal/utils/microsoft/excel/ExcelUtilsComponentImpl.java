package mx.com.tecnetia.orthogonal.utils.microsoft.excel;

import lombok.RequiredArgsConstructor;
import mx.com.tecnetia.orthogonal.dto.csv.CsvBean;
import mx.com.tecnetia.orthogonal.dto.excel.ExcelBean;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ExcelUtilsComponentImpl implements ExcelUtilsComponent {
    @Override
    public Workbook getWorkbook(String excelFilePath) throws IOException {
        Workbook workbook;
        if (excelFilePath == null) {
            workbook = new XSSFWorkbook();
        } else {
            if (excelFilePath.endsWith("xlsx")) {
                workbook = new XSSFWorkbook();
            } else if (excelFilePath.endsWith("xls")) {
                workbook = new HSSFWorkbook();
            } else {
                throw new IllegalArgumentException("El archivo especificado no es un excel.");
            }
        }
        return workbook;
    }

    @Override
    public <T extends ExcelBean<?>, U extends CsvBean> void writeExcelFromBeans(List<T> beans, String excelFilePath, Class<U> beanClass, String nombreHoja) throws IOException {
        Workbook workbook = this.getWorkbook(null);
        Sheet sheet = workbook.createSheet(nombreHoja);
        int rowCount = 0;
        Row row = sheet.createRow(rowCount);
        ExcelBean.writeHeadings(row, beanClass);
        for (var bean : beans) {
            row = sheet.createRow(++rowCount);
            bean.writeBook(row,workbook);
        }
        try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
            workbook.write(outputStream);
            workbook.close();
        }
    }
    
}

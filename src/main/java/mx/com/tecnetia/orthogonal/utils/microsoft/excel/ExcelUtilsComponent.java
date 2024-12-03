package mx.com.tecnetia.orthogonal.utils.microsoft.excel;

import mx.com.tecnetia.orthogonal.dto.csv.CsvBean;
import mx.com.tecnetia.orthogonal.dto.excel.ExcelBean;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.util.List;

public interface ExcelUtilsComponent {
    Workbook getWorkbook(String excelFilePath) throws IOException;

    <T extends ExcelBean<?>, U extends CsvBean> void writeExcelFromBeans(List<T> beans, String excelFilePath, Class<U> beanClass, String nombreHola) throws IOException;
}

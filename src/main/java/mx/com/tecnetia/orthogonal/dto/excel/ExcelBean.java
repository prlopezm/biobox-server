package mx.com.tecnetia.orthogonal.dto.excel;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import mx.com.tecnetia.orthogonal.dto.csv.CsvBean;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public abstract class ExcelBean<T extends CsvBean> {
    private final T csvBean;

    public abstract void writeBook(Row row, Workbook wb);

    public static void writeHeadings(Row header, Class<? extends CsvBean> bean) {
        int val = 0;
        Cell headerCell = header.createCell(val);
        for (var h : getHeadings(bean)) {
            headerCell.setCellValue(h);
            headerCell = header.createCell(++val);
        }
    }

    public static List<String> getHeadings(Class<? extends CsvBean> bean) {
        var ret = new ArrayList<String>();
        for (Field f : bean.getDeclaredFields()) {
            CsvBindByName column = f.getAnnotation(CsvBindByName.class);
            if (column != null)
                ret.add(column.column());
        }
        return ret;
    }
}

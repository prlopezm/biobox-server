package mx.com.tecnetia.orthogonal.utils.csv;


import com.opencsv.ICSVWriter;
import com.opencsv.bean.*;
import lombok.extern.log4j.Log4j2;
import mx.com.tecnetia.orthogonal.dto.csv.CsvBean;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Component
@Log4j2
public class CsvUtilsComponentImpl implements CsvUtilsComponent{

    @Override
    public <T extends CsvBean> List<T> buildListaCsv(Path path, Class<T> beanClass) throws Exception {
        var csvTransfer = new CsvTransferGeneric<T>();
        var ms = new HeaderColumnNameMappingStrategy<T>();
        ms.setType(beanClass);

        Reader reader = Files.newBufferedReader(path);
        var cb = new CsvToBeanBuilder<T>(reader)
                .withType(beanClass)
                .withMappingStrategy(ms)
                .build();

        csvTransfer.setCsvList(cb.parse());
        reader.close();
        return csvTransfer.getCsvList();
    }

    @Override
    public <T extends CsvBean> String writeCsvFromBeans(Path path, List<T> beans, Class<T> beanClass) {
        var ms = new HeaderColumnNameMappingStrategy<T>();
        ms.setType(beanClass);
        try {
            Writer writer = new FileWriter(path.toString());
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(writer)
                    .withSeparator(ICSVWriter.DEFAULT_SEPARATOR)
                    .withMappingStrategy(ms)
                    .build();
            beanToCsv.write(beans);
            writer.close();
        } catch (Exception ex) {
            log.info("Ocurrió el siguiente error al intentar crear el CSV: {}", ex.getMessage());
        }
        return CsvHelpers.readFile(path);
    }
}

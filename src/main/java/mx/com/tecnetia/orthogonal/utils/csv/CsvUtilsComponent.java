package mx.com.tecnetia.orthogonal.utils.csv;

import mx.com.tecnetia.orthogonal.dto.csv.CsvBean;

import java.nio.file.Path;
import java.util.List;

public interface CsvUtilsComponent {

    <T extends CsvBean> List<T> buildListaCsv(Path path, Class<T> beanClass) throws Exception;

    <T extends CsvBean> String writeCsvFromBeans(Path path, List<T> beans, Class<T> beanClass);
}

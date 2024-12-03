package mx.com.tecnetia.orthogonal.utils.csv;

import mx.com.tecnetia.orthogonal.dto.csv.CsvBean;

import java.util.ArrayList;
import java.util.List;

public class CsvTransfer {
    private List<String[]> csvStringList;

    private List<? extends CsvBean> csvList;

    public List<String[]> getCsvStringList() {
        if (csvStringList != null)
            return csvStringList;
        return new ArrayList<>();
    }

    public void addLine(String[] line) {
        if (this.csvList == null)
            this.csvStringList = new ArrayList<>();
        this.csvStringList.add(line);
    }

    public void setCsvStringList(List<String[]> csvStringList) {
        this.csvStringList = csvStringList;
    }

    public void setCsvList(List<? extends CsvBean> csvList) {
        this.csvList = csvList;
    }

    public List<? extends CsvBean> getCsvList() {
        if (csvList != null)
            return csvList;
        return new ArrayList<>();
    }
}


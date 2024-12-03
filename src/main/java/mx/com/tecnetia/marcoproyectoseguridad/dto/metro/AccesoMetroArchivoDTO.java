package mx.com.tecnetia.marcoproyectoseguridad.dto.metro;

import com.opencsv.bean.CsvBindByName;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.com.tecnetia.orthogonal.dto.csv.CsvBean;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO de accesos al metro reportados por STC en archivo de sincronizacion.")
public class AccesoMetroArchivoDTO extends CsvBean {
    @CsvBindByName(column = "Version", required = true)
    private String version;
    @CsvBindByName(column = "DateTime", required = true)
    private String dateTime;
    @CsvBindByName(column = "Station", required = true)
    private String station;
    @CsvBindByName(column = "Location", required = true)
    private String location;
    @CsvBindByName(column = "Barcode", required = true)
    private String barCode;
    @CsvBindByName(column = "Line", required = true)
    private String line;
    @CsvBindByName(column = "StationName", required = true)
    private String stationName;
    @CsvBindByName(column = "AuthorizationType", required = true)
    private String authorizationType;

}

package mx.com.tecnetia.marcoproyectoseguridad.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.com.tecnetia.marcoproyectoseguridad.dto.metro.AccesoMetroArchivoDTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.metro.QrMetroDTO;
import mx.com.tecnetia.marcoproyectoseguridad.mapper.QrMetroGeneradoMapper;
import mx.com.tecnetia.marcoproyectoseguridad.mapper.TransaccionMapper;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.FicheroTransaccionEntity;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository.FicheroTransaccionEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository.QrMetroGeneradoEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository.TransaccionEntityRepository;
import mx.com.tecnetia.orthogonal.utils.csv.CsvUtilsComponent;
import mx.com.tecnetia.orthogonal.utils.zip.ZipUtilsComponent;

@Service
@RequiredArgsConstructor
@Log4j2
public class MetroServiceImpl implements MetroService {
    private final QrMetroGeneradoEntityRepository qrMetroGeneradoEntityRepository;
    private final QrMetroGeneradoMapper qrMetroGeneradoMapper;
    private final ZipUtilsComponent zipUtilsComponent;
    private final CsvUtilsComponent csvUtilsComponent;
    private final FicheroTransaccionEntityRepository ficheroTransaccionEntityRepository;
    private final TransaccionEntityRepository transaccionEntityRepository;
    private final TransaccionMapper transaccionMapper;

    @Override
    @Transactional(readOnly = false)
    public void saveNuevosQr(@NotEmpty List<@NotNull QrMetroDTO> dtoList, @NotNull Long idArqUsuario) {
        dtoList.forEach(dto -> {
            var ent = this.qrMetroGeneradoMapper.toEntity(dto)
                    .setIdArqUsuario(idArqUsuario).setFechaSincronizacion(LocalDateTime.now());
            log.info("QRs generados: "+ent.getBarCode()+" -- "+ent.getIdArqUsuario()+" -- "+ent.getIdEstacionMetro());
            ent = this.qrMetroGeneradoEntityRepository.save(ent);
        });
    }

    @Override
    @Transactional(readOnly = false)
    @Scheduled(cron = "0 0 1 * * *")
    public void procesarSincronizacionMetro() {
        var rutaArchivos = "/Users/carlosruiz/Documents/Tc/Proyectos/BioBox/Sistema/archivosPrueba/";
        var nombreArchivoCSV = "syncbiobox.csv";
        var archivoZip = rutaArchivos+"syncbiobox.zip";
        var archivoTxt = rutaArchivos+"syncbiobox.txt";

       //descomprimimos el ZIP
        this.zipUtilsComponent.descomprimirArchivo(archivoZip,rutaArchivos);

       //Leemos el archivo TXT
        Path pathTxt = Paths.get(archivoTxt);
        List<String> allLinesTxt = new ArrayList<String>();
        try{
            //byte[] bytes = Files.readAllBytes(pathTxt);
            allLinesTxt = Files.readAllLines(pathTxt, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            throw new IllegalArgumentException("No se puede leer el archivo TXT.");
        }

       //Leemos el archivo CSV
        Path pathCsv = Paths.get(rutaArchivos+nombreArchivoCSV);
        List<AccesoMetroArchivoDTO> listaAccesosMetro = new ArrayList<AccesoMetroArchivoDTO>();
        try{
            listaAccesosMetro = this.csvUtilsComponent.buildListaCsv(pathCsv, AccesoMetroArchivoDTO.class);
        } catch (Exception ex) {
            throw new IllegalArgumentException("No se puede leer el archivo CSV.");
        }
        byte[] bytesCsv = null;
        try {
            bytesCsv = Files.readAllBytes(pathCsv);
        } catch (IOException ex) {
            throw new IllegalArgumentException("No se puede leer el archivo CSV.");
        }

        //guardamos registros en BD
        FicheroTransaccionEntity ficheroTransaccion = new FicheroTransaccionEntity()
                                                        .setFile(bytesCsv).setNombreFile(nombreArchivoCSV)
                                                        .setSignature(allLinesTxt.get(0))
                                                        .setCantidadRegistros(Integer.parseInt(allLinesTxt.get(1)))
                                                        .setKeyName(allLinesTxt.get(2));
        ficheroTransaccion = this.ficheroTransaccionEntityRepository.save(ficheroTransaccion);

        var idFicheroTransaccion = ficheroTransaccion.getIdFicheroTransaccion();
        listaAccesosMetro.forEach(dto -> {

            var ent = this.transaccionMapper.toEntity(dto)
                                .setIdFicheroTransaccion(idFicheroTransaccion);
            ent = this.transaccionEntityRepository.save(ent);
        });
    }
}

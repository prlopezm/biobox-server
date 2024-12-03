package mx.com.tecnetia.orthogonal.utils.zip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ZipUtilsComponentImpl implements ZipUtilsComponent {

    @Override
    @Transactional(readOnly = true)
    public void descomprimirArchivo(String ficheroZip, String directorioSalida) {
        final int TAM_BUFFER = 4096;
        byte[] buffer = new byte[TAM_BUFFER];

        ZipInputStream flujo = null;
        try {
            flujo = new ZipInputStream(new BufferedInputStream(
                    new FileInputStream(ficheroZip)));
            ZipEntry entrada;
            while ((entrada = flujo.getNextEntry()) != null) {
                String nombreSalida = directorioSalida + File.separator
                        + entrada.getName();
                if (entrada.isDirectory()) {
                    File directorio = new File(nombreSalida);
                    directorio.mkdir();
                } else {
                    BufferedOutputStream salida = null;
                    try {
                        int leido;
                        salida = new BufferedOutputStream(
                                new FileOutputStream(nombreSalida), TAM_BUFFER);
                        while ((leido = flujo.read(buffer, 0, TAM_BUFFER)) != -1) {
                            salida.write(buffer, 0, leido);
                        }
                    } finally {
                        if (salida != null) {
                            salida.close();
                        }
                    }
                }
            }
        } catch (IOException ex) {
            throw new IllegalArgumentException("No se puede descomprimir el archivo.");
        } finally {
            if (flujo != null) {
                try {
                    flujo.close();
                }catch (IOException ex){
                }
            }

        }
    }
}

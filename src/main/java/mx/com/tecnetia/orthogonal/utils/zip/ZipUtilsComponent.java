package mx.com.tecnetia.orthogonal.utils.zip;

import jakarta.validation.constraints.NotNull;

public interface ZipUtilsComponent {

    void descomprimirArchivo(@NotNull String archivoZip, @NotNull String directorioSalida);
    
}

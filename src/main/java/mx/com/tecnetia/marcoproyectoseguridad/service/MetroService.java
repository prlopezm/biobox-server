package mx.com.tecnetia.marcoproyectoseguridad.service;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import mx.com.tecnetia.marcoproyectoseguridad.dto.metro.QrMetroDTO;

import java.util.List;

public interface MetroService {

    void saveNuevosQr(@NotEmpty List<@NotNull QrMetroDTO> dtoList, @NotNull Long idArqUsuario);
    void procesarSincronizacionMetro();
}

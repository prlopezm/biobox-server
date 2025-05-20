package mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.persistence.entity.CuponerappCategoriaEstadoEntity;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO devuelto con los IDs de los canjes y sus puntos necesarios.")
public class CuponDTO implements Serializable {
    @NotNull
    Integer promoId;
    @NotNull
    Integer puntos;
    String promocion;
    String descripcion;
    String url;
    Integer idCompra;

    public CuponDTO(CuponerappCategoriaEstadoEntity cuponerappCategoriaEstadoEntity) {
        this.promoId = cuponerappCategoriaEstadoEntity.getCuponerapp().getIdPromocion();
        this.puntos = cuponerappCategoriaEstadoEntity.getCuponerapp().getPuntos();
        this.promocion = cuponerappCategoriaEstadoEntity.getCuponerapp().getPromocion();
        this.descripcion = cuponerappCategoriaEstadoEntity.getCuponerapp().getDescripcion();
        this.url = cuponerappCategoriaEstadoEntity.getCuponerapp().getUrl();
        this.idCompra = cuponerappCategoriaEstadoEntity.getCuponerapp().getIdCompra();
    }
}

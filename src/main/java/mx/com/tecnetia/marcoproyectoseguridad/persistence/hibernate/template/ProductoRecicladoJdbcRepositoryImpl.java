package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.template;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.DiaReciclajeBloqueadoDTO;
import mx.com.tecnetia.marcoproyectoseguridad.mapper.DiaReciclajeBloqueadoRowMapper;

@Repository
@RequiredArgsConstructor
public class ProductoRecicladoJdbcRepositoryImpl implements ProductoRecicladoJdbcRepository{
        private final JdbcTemplate jdbcTemplate;

        @Override
        public List<DiaReciclajeBloqueadoDTO> getDiasReciclajesBloaqueados(Long idUsuario, Integer maxFallasDiarias) {
            var sql = """
                select date(momento_reciclaje) as fecha, count(date(momento_reciclaje)) as cant
                from producto_reciclado pr where
                id_arq_usuario = ? and exitoso = false
                group by date(momento_reciclaje)
                having count(date(momento_reciclaje )) > ?
                order by date(momento_reciclaje)
                """;
            var listaRet = this.jdbcTemplate.query(sql, new DiaReciclajeBloqueadoRowMapper(), idUsuario, maxFallasDiarias);
            return listaRet;
        }

}

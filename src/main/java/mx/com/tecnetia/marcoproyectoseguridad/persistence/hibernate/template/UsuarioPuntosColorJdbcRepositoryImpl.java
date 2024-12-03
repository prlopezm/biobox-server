package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.template;

import lombok.RequiredArgsConstructor;
import mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.MovimientosDTO;
import mx.com.tecnetia.marcoproyectoseguridad.mapper.MovimientosUsuarioRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UsuarioPuntosColorJdbcRepositoryImpl implements UsuarioPuntosColorJdbcRepository{
        private final JdbcTemplate jdbcTemplate;

        @Override
        public List<MovimientosDTO> getMovimientosDeUsuario(Long idUsuario) {
            var sql = """
                select 
            t.id_producto_reciclado as idProductoReciclado, t.momento_reciclaje as fechaHora, t.nombre as descripcion, t.tipo as tipoMovimiento from
            (
                   select pr.id_producto_reciclado,pr.momento_reciclaje,sm.nombre ,1 as tipo from
                     usuario_puntos_color_acumulado upca
                     inner join producto_reciclado pr on pr.id_producto_reciclado = upca.id_producto_reciclado
                     inner join producto_reciclable pr2 on pr2.id_producto_reciclable = pr.id_producto_reciclable
                     inner join sub_marca sm on sm.id_sub_marca = pr2.id_sub_marca
                    where
                    pr.id_arq_usuario= ?
                   group by pr.id_producto_reciclado, sm.nombre
               union
                   select su.id_servicio_usado , su.momento , concat(cp.nombre,' ',pp.codigo), 2 from
                     usuario_puntos_color_consumidos upca
                     inner join servicio_usado su on su.id_servicio_usado = upca.id_servicio_usado
                     inner join programa_prontipago pp on pp.id_programa_prontipago = su.id_programa_prontipago
                     inner join categoria_prontipago cp  on cp.id_categoria_prontipago = pp.id_categoria_prontipago
                    where
                    su.id_arq_usuario = ?
                   group by su.id_servicio_usado , cp.nombre,pp.codigo
                ) as t
               order by t.momento_reciclaje desc
               limit 30
                """;
            var listaRet = this.jdbcTemplate.query(sql, new MovimientosUsuarioRowMapper(), idUsuario, idUsuario);
            return listaRet;
        }

}

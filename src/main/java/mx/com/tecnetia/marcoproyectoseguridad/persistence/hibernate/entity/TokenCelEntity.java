package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "token_cel", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class TokenCelEntity {
    @Basic
    @Column(name = "token")
    private String token;
    @Basic
    @Column(name = "alta")
    private Timestamp alta;
    @Id
    @Column(name = "id_arq_usuario")
    private Long idArqUsuario;

}

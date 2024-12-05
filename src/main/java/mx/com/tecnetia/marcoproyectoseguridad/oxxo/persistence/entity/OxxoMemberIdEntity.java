package mx.com.tecnetia.marcoproyectoseguridad.oxxo.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Table(name = "oxxo_member_id")
public class OxxoMemberIdEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "arq_usuario_id", nullable = false, length = Integer.MAX_VALUE)
    private Long arqUsuarioId;

    @Size(max = 20)
    @NotNull
    @Column(name = "member_id", nullable = false, length = 20)
    private String memberId;

}

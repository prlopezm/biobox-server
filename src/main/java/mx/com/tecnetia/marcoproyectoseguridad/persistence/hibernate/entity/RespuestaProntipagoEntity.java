package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "respuesta_prontipago", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class RespuestaProntipagoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_respuesta_prontipago")
    private Long idRespuestaProntipago;
    @Basic
    @Column(name = "id_servicio_usado",nullable = true)
    private Long idServicioUsado;
    @Basic
    @Column(name = "id_programa_prontipago",nullable = false)
    private Long idProgramaProntipago;
    @Basic
    @Column(name = "id_arq_usuario",nullable = false)
    private Long idArqUsuario;
    @Basic
    @Column(name = "code_transaction",nullable = true)
    private String codeTransaction;
    @Basic
    @Column(name = "status_transaction",nullable = true)
    private String statusTransaction;
    @Basic
    @Column(name = "codeDescription",nullable = true)
    private String codeDescription;
    @Basic
    @Column(name = "date_transaction",nullable = true)
    private String dateTransaction;
    @Basic
    @Column(name = "transaction_id",nullable = true)
    private String transactionId;
    @Basic
    @Column(name = "folio_transaction",nullable = true)
    private String folioTransaction;
    @Basic
    @Column(name = "transaction_status_description",nullable = true)
    private String transactionStatusDescription;
    @Basic
    @Column(name = "additional_info",nullable = true)
    private String additionalInfo;
}

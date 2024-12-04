package mx.com.tecnetia.orthogonal.utils.propiedades;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import mx.com.tecnetia.orthogonal.persistence.hibernate.entity.ArqPropiedadEntity;
import mx.com.tecnetia.orthogonal.persistence.hibernate.repository.ArqPropiedadEntityRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Pablo Rafael López Martínez <prlopezm@gmail.com>
 * @version 1.0 2024-12-03
 */
@Component
@Configuration
@AllArgsConstructor
public class PropiedadComponent {
    @Getter
    private List<ArqPropiedadEntity> propiedades;
    private final ArqPropiedadEntityRepository arqPropiedadEntityRepository;

    @PostConstruct
    void init(){
        this.propiedades = this.arqPropiedadEntityRepository.findAll();
    }
}

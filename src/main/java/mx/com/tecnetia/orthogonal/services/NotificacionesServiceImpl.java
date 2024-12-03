package mx.com.tecnetia.orthogonal.services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.TokenCelEntity;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository.TokenCelEntityRepository;

@Service
@RequiredArgsConstructor
public class NotificacionesServiceImpl implements NotificacionesService {
    private final TokenCelEntityRepository tokenCelEntityRepository;

    @Override
    @Transactional
    public void guardarToken(Long idUsuario, String token) {
            Optional<TokenCelEntity> tokenCelOpt = this.tokenCelEntityRepository.findByToken(token);
            TokenCelEntity tokenEntity;
            if(tokenCelOpt.isPresent()){
                if(tokenCelOpt.get().getIdArqUsuario().equals(idUsuario)){
                    tokenEntity = tokenCelOpt.get();
                }else {
                    TokenCelEntity usuarioActual = tokenCelOpt.get();
                    usuarioActual.setToken(null);
                    usuarioActual.setAlta(Timestamp.valueOf(LocalDateTime.now()));
                    this.tokenCelEntityRepository.save(usuarioActual);

                    Optional<TokenCelEntity> tokenCel2Opt = this.tokenCelEntityRepository.findById(idUsuario);
                    if (tokenCel2Opt.isPresent()) {
                        tokenEntity = tokenCel2Opt.get();
                    } else {
                        tokenEntity = new TokenCelEntity();
                        tokenEntity.setIdArqUsuario(idUsuario);
                    }
                }
            }else{
                Optional<TokenCelEntity> tokenCel2Opt = this.tokenCelEntityRepository.findById(idUsuario);

                if (tokenCel2Opt.isPresent()) {
                    tokenEntity = tokenCel2Opt.get();
                } else {
                    tokenEntity = new TokenCelEntity();
                    tokenEntity.setIdArqUsuario(idUsuario);
                }
            }

            tokenEntity.setToken(token);
            tokenEntity.setAlta(Timestamp.valueOf(LocalDateTime.now()));
            this.tokenCelEntityRepository.save(tokenEntity);
    }

}

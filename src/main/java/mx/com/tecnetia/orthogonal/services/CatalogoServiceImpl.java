package mx.com.tecnetia.orthogonal.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.com.tecnetia.orthogonal.dto.MailDTO;
import mx.com.tecnetia.orthogonal.dto.RolDTO;
import mx.com.tecnetia.orthogonal.dto.ServicioRestDTO;
import mx.com.tecnetia.orthogonal.persistence.hibernate.repository.ArqRolAplicacionRepository;
import mx.com.tecnetia.orthogonal.persistence.hibernate.repository.ArqServicioRestRepository;
import mx.com.tecnetia.orthogonal.utils.email.EMailOperationsFreeMarkerService;
import mx.com.tecnetia.orthogonal.utils.email.EmailOperationsThymeleafService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Log4j2
public class CatalogoServiceImpl implements CatalogoService {
    private final ArqServicioRestRepository arqServicioRestRepository;
    private final EMailOperationsFreeMarkerService eMailOperationsFreeMarkerService;
    private final EmailOperationsThymeleafService emailOperationsThymeleafService;
    private final ArqRolAplicacionRepository arqRolAplicacionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ServicioRestDTO> getCatalogoServiciosRest() {
        this.sendEmailThymeleaf();
        return arqServicioRestRepository.getServicios();
    }

    @Override
    @Transactional(readOnly = true)
    public CompletableFuture<List<ServicioRestDTO>> getCatalogoServiciosRest2() {
        this.sendEmailThymeleaf();
        return CompletableFuture.completedFuture(arqServicioRestRepository.getServicios());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RolDTO> getAllRoles() {
        return this.arqRolAplicacionRepository.getAllRolesDTO();
    }

    public void sendEmailFreemaker() {
        var mailDTO = new MailDTO();
        var templateName = "email-bienvenida-freemarker.ftl";

        mailDTO.setMailTo("prlopezm@gmail.com")
                .setMailSubject("Mi email desde la app de aquitectura usando FreeMaker.");

        var model = new HashMap<String, Object>();
        model.put("name", "Hola pablito");

        mailDTO.setModel(model);
        try {
            this.eMailOperationsFreeMarkerService.sendEmail(mailDTO, templateName);
        } catch (Exception ex) {
            log.error("Ocurrió un error al enviar el email: {}", ex.getMessage());
        }

    }

    public void sendEmailThymeleaf() {
        var mailDTO = new MailDTO();
        var templateName = "email-bienvenida-thymeleaf.html";

        mailDTO.setMailTo("prlopezm@gmail.com")
                .setMailSubject("Mi email desde la app de arquitectura usando Thymeleaf.");

        var model = new HashMap<String, Object>();
        model.put("name", "Pablo López.");

        mailDTO.setModel(model);
        try {
            this.emailOperationsThymeleafService.sendEmail(mailDTO, templateName);
        } catch (Exception ex) {
            log.error("Ocurrió un error al enviar el email: {}", ex.getMessage());
        }
    }
}

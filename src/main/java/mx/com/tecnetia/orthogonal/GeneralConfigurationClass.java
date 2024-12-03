package mx.com.tecnetia.orthogonal;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.com.tecnetia.orthogonal.beans.ServicioRestDTOBean;
import mx.com.tecnetia.orthogonal.persistence.hibernate.repository.ArqServicioRestRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
@Log4j2
@RequiredArgsConstructor
@ComponentScan(basePackages = "mx.com.tecnetia")
public class GeneralConfigurationClass {
    private final ArqServicioRestRepository arqServicioRestRepository;

    @Bean
    public ServicioRestDTOBean getServicioRestDTOBean() {
        var ret = new ServicioRestDTOBean();
        log.info("Dentro del constructor del Bean. Accediendo a la BD.");
        ret.setListaServiciosActivos(arqServicioRestRepository.getServicios());
        return ret;
    }

    @Bean(name = "asyncExecutor")
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(3);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("HiloAsincrono-");
        executor.initialize();
        return executor;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

/*    @Bean(name = "applicationEventMulticaster")
    public ApplicationEventMulticaster simpleApplicationEventMulticaster() {
        SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();
        eventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return eventMulticaster;
    }*/



}

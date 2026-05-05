package com.proyectoparcial.acortadorenlaces.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync // <--- ESTO ES LO QUE ACTIVA LA CONCURRENCIA
public class AsyncConfig {

    @Bean(name = "acortadorExecutor")
    public Executor acortadorExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5); // Hilos mínimos activos
        executor.setMaxPoolSize(10); // Hilos máximos si hay mucha carga
        executor.setQueueCapacity(500); // Cola de espera para peticiones
        executor.setThreadNamePrefix("AcortadorThread-");
        executor.initialize();
        return executor;
    }
}
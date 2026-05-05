package com.proyectoparcial.acortadorenlaces;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @EnableAsync se elimina de aquí — ya está definido en AsyncConfig.java
// Tenerlo en ambos lados es redundante y causa error si falta el import
@SpringBootApplication
public class AcortadorEnlacesApplication {
    public static void main(String[] args) {
        SpringApplication.run(AcortadorEnlacesApplication.class, args);
    }
}

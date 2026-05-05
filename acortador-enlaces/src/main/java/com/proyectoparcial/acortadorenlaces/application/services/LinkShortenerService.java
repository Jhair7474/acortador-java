package com.proyectoparcial.acortadorenlaces.application.services;

import com.proyectoparcial.acortadorenlaces.domain.model.Link;
import com.proyectoparcial.acortadorenlaces.domain.ports.LinkRepositoryPort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class LinkShortenerService {
    private final LinkRepositoryPort repository;

    public LinkShortenerService(LinkRepositoryPort repository) {
        this.repository = repository;
    }

    // QUITAMOS EL @Async DE AQUÍ PARA VALIDAR EN EL HILO PRINCIPAL
    public String validateAndGenerateCode(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new InvalidDescriptionException("La descripción no puede estar vacía");
        }
        long wordCount = Arrays.stream(description.split("\\s+")).count();
        if (wordCount < 5) {
            throw new InvalidDescriptionException("La descripción debe tener al menos 5 palabras");
        }
        return UUID.randomUUID().toString().substring(0, 8);
    }

    @Async("acortadorExecutor")
    public CompletableFuture<String> saveLinkAsync(String originalUrl, String imageUrl, String description,
            String shortCode) {
        // URL más limpia
        String finalUrl = "http://localhost:8080/" + shortCode;

        Link link = new Link(originalUrl, finalUrl, imageUrl, description);
        repository.save(link);

        return CompletableFuture.completedFuture(link.getShortenedUrl());
    }
}
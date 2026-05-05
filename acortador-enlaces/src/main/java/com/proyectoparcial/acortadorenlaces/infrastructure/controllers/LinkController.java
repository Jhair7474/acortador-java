package com.proyectoparcial.acortadorenlaces.infrastructure.controllers;

import com.proyectoparcial.acortadorenlaces.application.dtos.LinkRequest;
import com.proyectoparcial.acortadorenlaces.application.services.LinkShortenerService;
import com.proyectoparcial.acortadorenlaces.domain.model.Link;
import com.proyectoparcial.acortadorenlaces.domain.ports.LinkRepositoryPort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class LinkController {

    private final LinkShortenerService service;
    private final LinkRepositoryPort repository;

    public LinkController(LinkShortenerService service, LinkRepositoryPort repository) {
        this.service = service;
        this.repository = repository;
    }

    @PostMapping("/api/links")
    public CompletableFuture<String> createLink(@RequestBody LinkRequest request) {
        System.out.println("DEBUG: Recibido de Frontend -> Imagen: " + request.getImageUrl() + ", Desc: "
                + request.getDescription());
        String shortCode = service.validateAndGenerateCode(request.getDescription());
        return service.saveLinkAsync(
                request.getOriginalUrl(),
                request.getImageUrl(),
                request.getDescription(),
                shortCode);
    }

    @GetMapping("/api/links")
    public List<Link> getAllLinks() {
        return repository.findAll();
    }

    @GetMapping("/{code}")
    public void redirectToOriginalUrl(@PathVariable String code, HttpServletResponse response) throws IOException {
      
        Link link = repository.findAll().stream()
                .filter(l -> l.getShortenedUrl().endsWith(code))
                .findFirst()
                .orElse(null);

        if (link != null) {
            response.sendRedirect(link.getOriginalUrl());
        } else {
            response.sendError(404, "Enlace no encontrado");
        }
    }
}
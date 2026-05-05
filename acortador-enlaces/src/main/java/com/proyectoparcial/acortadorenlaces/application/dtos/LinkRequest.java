package com.proyectoparcial.acortadorenlaces.application.dtos;

public class LinkRequest {
    private String originalUrl;
    private String imageUrl;
    private String description;

    // Getters y Setters
    public String getOriginalUrl() { return originalUrl; }
    public void setOriginalUrl(String originalUrl) { this.originalUrl = originalUrl; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
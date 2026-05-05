package com.proyectoparcial.acortadorenlaces.domain.model;

public class Link {
    private String originalUrl;
    private String shortenedUrl;
    private String imageUrl;
    private String description;

    // Constructor completo — requerido por LinkShortenerService
    public Link(String originalUrl, String shortenedUrl, String imageUrl, String description) {
        this.originalUrl = originalUrl;
        this.shortenedUrl = shortenedUrl;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    // Getters
    public String getOriginalUrl()  { return originalUrl; }
    public String getShortenedUrl() { return shortenedUrl; }
    public String getImageUrl()     { return imageUrl; }
    public String getDescription()  { return description; }

    // Setters
    public void setOriginalUrl(String originalUrl)   { this.originalUrl = originalUrl; }
    public void setShortenedUrl(String shortenedUrl) { this.shortenedUrl = shortenedUrl; }
    public void setImageUrl(String imageUrl)         { this.imageUrl = imageUrl; }
    public void setDescription(String description)   { this.description = description; }
}

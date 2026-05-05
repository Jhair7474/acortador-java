package com.proyectoparcial.acortadorenlaces.infrastructure.adapters;

import com.proyectoparcial.acortadorenlaces.domain.model.Link;
import com.proyectoparcial.acortadorenlaces.domain.ports.LinkRepositoryPort;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.bson.Document;

import java.util.List;

@Component
public class MultiDbAdapter implements LinkRepositoryPort {

    private final JdbcTemplate jdbc;
    private final MongoTemplate mongo;
    private final RedisTemplate<String, String> redis;

    public MultiDbAdapter(
            JdbcTemplate jdbc,
            MongoTemplate mongo,
            @Qualifier("redisTemplate") RedisTemplate<String, String> redis) {
        this.jdbc = jdbc;
        this.mongo = mongo;
        this.redis = redis;
    }

    @Override
    public void save(Link link) {
        // 1. MySQL
        jdbc.update(
                "INSERT INTO links (original_url, shortened_url) VALUES (?, ?)",
                link.getOriginalUrl(),
                link.getShortenedUrl());

        // 2. MongoDB: Guardado en la colección "enlace"
        try {
            Document doc = new Document()
                    .append("shortened_url", link.getShortenedUrl())
                    .append("image_url", link.getImageUrl())
                    .append("description", link.getDescription());

            // --- CAMBIO AQUÍ: Nombre de la colección ---
            mongo.insert(doc, "enlace");
            System.out.println("🍃 [MONGO] Guardado en colección 'enlace': " + link.getDescription());
        } catch (Exception e) {
            System.err.println("❌ [MONGO ERROR]: " + e.getMessage());
        }

        // 3. Redis
        if (link.getOriginalUrl() != null && link.getOriginalUrl().length() >= 50) {
            try {
                redis.opsForValue().set(link.getShortenedUrl(), link.getOriginalUrl());
                System.out.println("🚀 [REDIS] Cacheado.");
            } catch (Exception e) {
                System.err.println("⚠️ [REDIS OFF]");
            }
        }
    }

    @Override
    public List<Link> findAll() {
        List<Link> links = jdbc.query(
                "SELECT original_url, shortened_url FROM links",
                (rs, rowNum) -> new Link(
                        rs.getString("original_url"),
                        rs.getString("shortened_url"),
                        null,
                        null));

        links.forEach(link -> {
            try {
                Query query = new Query(Criteria.where("shortened_url").is(link.getShortenedUrl()));

                // --- CAMBIO AQUÍ: Nombre de la colección ---
                Document doc = mongo.findOne(query, Document.class, "enlace");

                if (doc != null) {
                    link.setImageUrl(doc.getString("image_url"));
                    link.setDescription(doc.getString("description"));
                }
            } catch (Exception e) {
                System.err.println("⚠️ Error en Mongo: " + e.getMessage());
            }
        });

        return links;
    }
}
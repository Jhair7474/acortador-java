package com.proyectoparcial.acortadorenlaces.domain.ports;

import com.proyectoparcial.acortadorenlaces.domain.model.Link;
import java.util.List;

public interface LinkRepositoryPort {
    void save(Link link);
    List<Link> findAll();
}
